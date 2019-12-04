package co.cambridgetechnology.auction.core.memory.service;

import co.cambridgetechnology.auction.core.memory.entity.AuctionItem;
import co.cambridgetechnology.auction.core.memory.model.request.impl.AcceptRequest;
import co.cambridgetechnology.auction.core.memory.model.request.impl.BidRequest;
import co.cambridgetechnology.auction.core.memory.model.request.impl.OfferRequest;

public interface AuctionService {
    /**
     * Create an {@link AuctionItem} in serialized isolated transaction
     * @param offerRequest all the info needed for creating {@link AuctionItem}
     * @return {@link AuctionItem} successfully created
     */
    AuctionItem offer(OfferRequest offerRequest);

    AuctionItem bid (BidRequest bidRequest);

    AuctionItem accept(AcceptRequest acceptRequest);
}
