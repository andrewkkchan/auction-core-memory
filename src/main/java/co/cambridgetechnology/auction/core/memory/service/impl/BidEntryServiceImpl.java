package co.cambridgetechnology.auction.core.memory.service.impl;

import co.cambridgetechnology.auction.core.memory.entity.BidEntry;
import co.cambridgetechnology.auction.core.memory.model.request.impl.BidRequest;
import co.cambridgetechnology.auction.core.memory.repository.BidEntryRepository;
import co.cambridgetechnology.auction.core.memory.service.BidEntryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BidEntryServiceImpl implements BidEntryService {
    private final BidEntryRepository bidEntryRepository;

    public BidEntryServiceImpl(BidEntryRepository bidEntryRepository) {
        this.bidEntryRepository = bidEntryRepository;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Override
    public BidEntry record(String requestId, BidRequest request, Integer kafkaPartition, Long kafkaOffset) {
        BidEntry bidEntry= new BidEntry(request.getItemKey(), request.getBidder(), request.getBid());
        return bidEntryRepository.save(bidEntry);
    }
}
