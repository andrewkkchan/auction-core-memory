package co.cambridgetechnology.auction.core.memory.consumer.impl;

import co.cambridgetechnology.auction.core.memory.entity.AuctionItem;
import co.cambridgetechnology.auction.core.memory.entity.TransactionEvent;
import co.cambridgetechnology.auction.core.memory.entity.Type;
import com.fasterxml.jackson.databind.ObjectMapper;
import co.cambridgetechnology.auction.core.memory.consumer.Processor;
import co.cambridgetechnology.auction.core.memory.consumer.Producer;
import co.cambridgetechnology.auction.core.memory.exception.InvalidBusinessRuleException;
import co.cambridgetechnology.auction.core.memory.model.request.impl.OfferRequest;
import co.cambridgetechnology.auction.core.memory.service.AuctionService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Component
public class OfferProcessor implements Processor {
    private final ObjectMapper objectMapper;
    private final AuctionService auctionService;
    private final Producer producer;

    public OfferProcessor(ObjectMapper objectMapper, AuctionService auctionService, Producer producer) {
        this.objectMapper = objectMapper;
        this.auctionService = auctionService;
        this.producer = producer;
    }

    @Override
    public void process(TransactionEvent transactionEvent) {
        String requestId = transactionEvent.getId();
        OfferRequest offerRequest;
        try {
            offerRequest = objectMapper.readValue(transactionEvent.getRequest(), OfferRequest.class);
        } catch (IOException e) {
            producer.produceError(requestId, new InvalidBusinessRuleException("Malformed request"),
                    transactionEvent.getKafkaPartition(), transactionEvent.getKafkaOffset());
            return;
        }
        try {
            offerAndProduce(requestId, offerRequest, transactionEvent.getKafkaPartition(), transactionEvent.getKafkaOffset());
        } catch (InvalidBusinessRuleException e) {
            producer.produceError(requestId, e, transactionEvent.getKafkaPartition(), transactionEvent.getKafkaOffset());
        }
    }
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void offerAndProduce(String requestId, OfferRequest offerRequest, Integer kafkaPartition, long kafkaOffset) {
        AuctionItem auctionItem = this.auctionService.offer(offerRequest);
        producer.produceSuccess(requestId, auctionItem, kafkaPartition, kafkaOffset);
    }

    @Override
    public String getType() {
        return Type.OFFER.toString();
    }
}
