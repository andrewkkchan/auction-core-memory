package co.cambridgetechnology.auction.core.memory.model.request.impl;

import co.cambridgetechnology.auction.core.memory.model.request.EventRequest;

import java.math.BigDecimal;

public class BidRequest implements EventRequest {
    private String itemKey;
    private String bidder;
    private BigDecimal bid;

    public BidRequest(String itemKey, String bidder, BigDecimal bid) {
        this.itemKey = itemKey;
        this.bidder = bidder;
        this.bid = bid;
    }

    public String getItemKey() {
        return itemKey;
    }

    public void setItemKey(String itemKey) {
        this.itemKey = itemKey;
    }

    public String getBidder() {
        return bidder;
    }

    public void setBidder(String bidder) {
        this.bidder = bidder;
    }

    public BigDecimal getBid() {
        return bid;
    }

    public void setBid(BigDecimal bid) {
        this.bid = bid;
    }
}
