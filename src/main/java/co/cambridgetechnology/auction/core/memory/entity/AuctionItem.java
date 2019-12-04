package co.cambridgetechnology.auction.core.memory.entity;

import java.io.Serializable;
import java.math.BigDecimal;

public class AuctionItem implements Serializable {

    private String itemKey;

    private String offeror;

    private String bidder;

    private BigDecimal highestBid;

    private boolean isAccepted;

    public AuctionItem(String itemKey, String offeror) {
        this.itemKey = itemKey;
        this.offeror = offeror;
        this.bidder = null;
        this.highestBid = BigDecimal.ZERO;
        this.isAccepted = false;
    }

    public String getItemKey() {
        return itemKey;
    }

    public void setItemKey(String itemKey) {
        this.itemKey = itemKey;
    }

    public String getOfferor() {
        return offeror;
    }

    public void setOfferor(String offeror) {
        this.offeror = offeror;
    }

    public String getBidder() {
        return bidder;
    }

    public void setBidder(String bidder) {
        this.bidder = bidder;
    }

    public BigDecimal getHighestBid() {
        return highestBid;
    }

    public void setHighestBid(BigDecimal highestBid) {
        this.highestBid = highestBid;
    }

    public boolean isAccepted() {
        return isAccepted;
    }

    public void setAccepted(boolean accepted) {
        isAccepted = accepted;
    }
}
