package co.cambridgetechnology.auction.core.memory.service;

import co.cambridgetechnology.auction.core.memory.entity.BidEntry;
import co.cambridgetechnology.auction.core.memory.model.request.impl.BidRequest;

public interface BidEntryService {
    BidEntry record(String requestId, BidRequest request, Integer kafkaPartition, Long kafkaOffset);

}
