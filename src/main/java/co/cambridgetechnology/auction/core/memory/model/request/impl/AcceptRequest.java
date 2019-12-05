package co.cambridgetechnology.auction.core.memory.model.request.impl;

import co.cambridgetechnology.auction.core.memory.model.request.EventRequest;

public class AcceptRequest implements EventRequest {
    private String itemKey;
    public String getItemKey() {
        return itemKey;
    }

    public void setItemKey(String itemKey) {
        this.itemKey = itemKey;
    }

    public AcceptRequest(String itemKey) {
        this.itemKey = itemKey;
    }

    public AcceptRequest() {
    }


}
