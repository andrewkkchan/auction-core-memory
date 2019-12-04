package co.cambridgetechnology.auction.core.memory.service.impl;

import co.cambridgetechnology.auction.core.memory.entity.AuctionItem;
import co.cambridgetechnology.auction.core.memory.exception.InvalidBusinessRuleException;
import co.cambridgetechnology.auction.core.memory.model.request.impl.AcceptRequest;
import co.cambridgetechnology.auction.core.memory.model.request.impl.BidRequest;
import co.cambridgetechnology.auction.core.memory.model.request.impl.OfferRequest;
import co.cambridgetechnology.auction.core.memory.repository.AuctionRepository;
import co.cambridgetechnology.auction.core.memory.service.AuctionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class AuctionServiceImpl implements AuctionService {
    private final AuctionRepository auctionRepository;

    public AuctionServiceImpl(AuctionRepository auctionRepository) {
        this.auctionRepository = auctionRepository;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Override
    public AuctionItem offer(OfferRequest offerRequest) {
        if (auctionRepository.existsByItemKey(offerRequest.getItemKey())) {
            throw new InvalidBusinessRuleException("Auction of the item already existed");
        }
        AuctionItem auctionItem = new AuctionItem(offerRequest.getItemKey(), offerRequest.getOfferor());
        return auctionRepository.save(auctionItem);
    }

    @Override
    public AuctionItem bid(BidRequest bidRequest) {
        if (!auctionRepository.existsByItemKey(bidRequest.getItemKey())) {
            throw new InvalidBusinessRuleException("Auction of the item does not exist-- your bid is not allowed");
        }
        Optional<AuctionItem> auctionOptional = auctionRepository.findByItemKey(bidRequest.getItemKey());
        if (!auctionOptional.isPresent()) {
            throw new InvalidBusinessRuleException("Auction of the item does not exist-- your bid is not allowed");
        }
        AuctionItem auctionItem = auctionOptional.get();
        if (bidRequest.getBid().compareTo(auctionItem.getHighestBid()) < 0) {
            throw new InvalidBusinessRuleException("Your bid is lower or equal to the highest bid-- your bid is not allowed");
        }
        if (auctionItem.isAccepted()) {
            throw new InvalidBusinessRuleException("The auction is closed as another bid has been accepted -- your bid is not allowed");
        }
        auctionItem.setHighestBid(bidRequest.getBid());
        auctionItem.setBidder(bidRequest.getBidder());
        return auctionRepository.save(auctionItem);
    }

    @Override
    public AuctionItem accept(AcceptRequest acceptRequest) {
        if (!auctionRepository.existsByItemKey(acceptRequest.getItemKey())) {
            throw new InvalidBusinessRuleException("Auction of the item does not exist-- your acceptance is not allowed");
        }
        Optional<AuctionItem> auctionOptional = auctionRepository.findByItemKey(acceptRequest.getItemKey());
        if (!auctionOptional.isPresent()) {
            throw new InvalidBusinessRuleException("Auction of the item does not exist-- your acceptance is not allowed");
        }
        AuctionItem auctionItem = auctionOptional.get();
        if (auctionItem.getHighestBid().equals(BigDecimal.ZERO)){
            throw new InvalidBusinessRuleException("The auction has no bid -- your acceptance is not allowed");
        }
        if (auctionItem.isAccepted()) {
            throw new InvalidBusinessRuleException("The auction has been accepted -- your acceptance is not allowed");
        }
        auctionItem.setAccepted(true);
        return auctionRepository.save(auctionItem);
    }
}

