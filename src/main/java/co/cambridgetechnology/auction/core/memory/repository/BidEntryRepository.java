package co.cambridgetechnology.auction.core.memory.repository;

import co.cambridgetechnology.auction.core.memory.entity.BidEntry;

public interface BidEntryRepository {
    BidEntry save(BidEntry bidEntry);

    Iterable<BidEntry> findAllByItemKey(String itemKey);

    Iterable<BidEntry> findAllByBidder(String bidder);

    Iterable<BidEntry> findAll();
}
