package co.cambridgetechnology.auction.core.memory.repository.impl;

import co.cambridgetechnology.auction.core.memory.entity.BidEntry;
import co.cambridgetechnology.auction.core.memory.repository.BidEntryRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class BidEntryRepositoryImpl implements BidEntryRepository {
    private final List<BidEntry> bidEntries = new ArrayList<>();

    @Override
    public BidEntry save(BidEntry bidEntry) {
        bidEntries.add(bidEntry);
        return bidEntry;
    }

    @Override
    public Iterable<BidEntry> findAllByItemKey(String itemKey) {
        List<BidEntry> result = new ArrayList<>();
        for (BidEntry bidEntry : bidEntries) {
            if (bidEntry.getItemKey().equals(itemKey)) {
                result.add(bidEntry);
            }
        }
        return result;
    }

    @Override
    public Iterable<BidEntry> findAllByBidder(String bidder) {
        List<BidEntry> result = new ArrayList<>();
        for (BidEntry bidEntry : bidEntries) {
            if (bidEntry.getBidder().equals(bidder)) {
                result.add(bidEntry);
            }
        }
        return result;
    }

    @Override
    public Iterable<BidEntry> findAll() {
        return bidEntries;
    }
}
