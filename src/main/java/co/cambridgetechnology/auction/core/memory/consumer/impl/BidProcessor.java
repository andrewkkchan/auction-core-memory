package co.cambridgetechnology.auction.core.memory.consumer.impl;

import co.cambridgetechnology.auction.core.memory.entity.AuctionItem;
import co.cambridgetechnology.auction.core.memory.entity.TransactionEvent;
import co.cambridgetechnology.auction.core.memory.entity.Type;
import com.fasterxml.jackson.databind.ObjectMapper;
import co.cambridgetechnology.auction.core.memory.consumer.Processor;
import co.cambridgetechnology.auction.core.memory.consumer.Producer;
import co.cambridgetechnology.auction.core.memory.entity.BidEntry;
import co.cambridgetechnology.auction.core.memory.exception.InvalidBusinessRuleException;
import co.cambridgetechnology.auction.core.memory.model.request.impl.BidRequest;
import co.cambridgetechnology.auction.core.memory.service.AuctionService;
import co.cambridgetechnology.auction.core.memory.service.BidEntryService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Component
public class BidProcessor implements Processor {
    private final ObjectMapper objectMapper;
    private final AuctionService auctionService;
    private final BidEntryService bidEntryService;
    private final Producer producer;

    public BidProcessor(ObjectMapper objectMapper, AuctionService auctionService, BidEntryService bidEntryService, Producer producer) {
        this.objectMapper = objectMapper;
        this.auctionService = auctionService;
        this.bidEntryService = bidEntryService;
        this.producer = producer;
    }

    @Override
    public void process(TransactionEvent transactionEvent) {
        String requestId = transactionEvent.getId();

        BidRequest bidRequest;
        try {
            bidRequest = objectMapper.readValue(transactionEvent.getRequest(), BidRequest.class);
        } catch (IOException e) {
            producer.produceError(requestId, new InvalidBusinessRuleException("Malformed request"),
                    transactionEvent.getKafkaPartition(), transactionEvent.getKafkaOffset());
            return;
        }
        try {
            bidAndProduce(requestId, bidRequest, transactionEvent.getKafkaPartition(), transactionEvent.getKafkaOffset());
        } catch (InvalidBusinessRuleException e) {
            producer.produceError(requestId, e, transactionEvent.getKafkaPartition(), transactionEvent.getKafkaOffset());
        }
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void bidAndProduce(String requestId, BidRequest bidRequest, Integer kafkaPartition, long kafkaOffset) {
        AuctionItem auctionItem = this.auctionService.bid(bidRequest);
        BidEntry bidEntry = this.bidEntryService.record(requestId, bidRequest, kafkaPartition, kafkaOffset);
        producer.produceSuccess(requestId, auctionItem, kafkaPartition, kafkaOffset);
    }

    @Override
    public String getType() {
        return Type.BID.toString();
    }
}
