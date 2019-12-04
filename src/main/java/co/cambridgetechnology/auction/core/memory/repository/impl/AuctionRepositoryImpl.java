package co.cambridgetechnology.auction.core.memory.repository.impl;

import co.cambridgetechnology.auction.core.memory.entity.AuctionItem;
import co.cambridgetechnology.auction.core.memory.repository.AuctionRepository;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class AuctionRepositoryImpl implements AuctionRepository {
    private final Map<String, AuctionItem> auctions = new HashMap<>();

    @Override
    public Optional<AuctionItem> findByItemKey(String itemKey) {
        return Optional.ofNullable(auctions.get(itemKey));    }

    @Override
    public AuctionItem save(AuctionItem auctionItem) {
        auctions.put(auctionItem.getItemKey(), auctionItem);
        return auctionItem;
    }

    @Override
    public boolean existsByItemKey(String itemKey) {
        return auctions.containsKey(itemKey);
    }

    @Override
    public Iterable<AuctionItem> findAll() {
        return auctions.values();
    }
}
