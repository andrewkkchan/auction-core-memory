package co.cambridgetechnology.auction.core.memory.consumer.impl;

import co.cambridgetechnology.auction.core.memory.entity.AuctionItem;
import co.cambridgetechnology.auction.core.memory.entity.TransactionEvent;
import co.cambridgetechnology.auction.core.memory.entity.Type;
import com.fasterxml.jackson.databind.ObjectMapper;
import co.cambridgetechnology.auction.core.memory.consumer.Processor;
import co.cambridgetechnology.auction.core.memory.consumer.Producer;
import co.cambridgetechnology.auction.core.memory.exception.InvalidBusinessRuleException;
import co.cambridgetechnology.auction.core.memory.model.request.impl.AcceptRequest;
import co.cambridgetechnology.auction.core.memory.service.AuctionService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Component
public class AcceptanceProcessor implements Processor {

    private final ObjectMapper objectMapper;
    private final AuctionService auctionService;
    private final Producer producer;

    public AcceptanceProcessor(ObjectMapper objectMapper, AuctionService auctionService, Producer producer) {
        this.objectMapper = objectMapper;
        this.auctionService = auctionService;
        this.producer = producer;
    }

    @Override
    public void process(TransactionEvent transactionEvent) {
        String requestId = transactionEvent.getId();

        AcceptRequest acceptRequest;
        try {
            acceptRequest = objectMapper.readValue(transactionEvent.getRequest(), AcceptRequest.class);
        } catch (IOException e) {
            producer.produceError(requestId, new InvalidBusinessRuleException("Malformed request"),
                    transactionEvent.getKafkaPartition(), transactionEvent.getKafkaOffset());
            return;
        }
        try {
            acceptAndProduce(requestId, acceptRequest, transactionEvent.getKafkaPartition(), transactionEvent.getKafkaOffset());
        } catch (InvalidBusinessRuleException e) {
            producer.produceError(requestId, e, transactionEvent.getKafkaPartition(), transactionEvent.getKafkaOffset());
        }
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void acceptAndProduce(String requestId, AcceptRequest acceptRequest, Integer kafkaPartition, long kafkaOffset) {
        AuctionItem auctionItem = this.auctionService.accept(acceptRequest);
        producer.produceSuccess(requestId, auctionItem, kafkaPartition, kafkaOffset);
    }

    @Override
    public String getType() {
        return Type.ACCEPTANCE.toString();
    }
}
