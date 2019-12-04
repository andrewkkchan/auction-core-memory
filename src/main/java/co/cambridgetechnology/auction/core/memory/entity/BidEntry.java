package co.cambridgetechnology.auction.core.memory.entity;

import java.math.BigDecimal;

/**
 * One of the only TWO building block for ledger, apart from {@link AuctionItem}
 * Represents the only allowable actions to be applied onto any {@link AuctionItem}
 */

public class BidEntry {
    private String itemKey;
    private String bidder;
    private BigDecimal bid;

    public BidEntry(String itemKey, String bidder, BigDecimal bid) {
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
