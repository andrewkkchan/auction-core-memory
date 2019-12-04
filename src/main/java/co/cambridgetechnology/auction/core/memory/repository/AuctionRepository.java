package co.cambridgetechnology.auction.core.memory.repository;

import co.cambridgetechnology.auction.core.memory.entity.AuctionItem;

import java.util.Optional;

public interface AuctionRepository {
    Optional<AuctionItem> findByItemKey(String itemKey);

    AuctionItem save(AuctionItem auctionItem);

    boolean existsByItemKey(String itemKey);

    Iterable<AuctionItem> findAll();
}
