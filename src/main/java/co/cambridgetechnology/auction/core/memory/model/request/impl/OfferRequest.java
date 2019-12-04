package co.cambridgetechnology.auction.core.memory.model.request.impl;

import co.cambridgetechnology.auction.core.memory.model.request.EventRequest;

public class OfferRequest implements EventRequest {
    private String itemKey;
    private String offeror;

    public OfferRequest(String itemKey, String offeror) {
        this.itemKey = itemKey;
        this.offeror = offeror;
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

    public OfferRequest() {
    }
}
