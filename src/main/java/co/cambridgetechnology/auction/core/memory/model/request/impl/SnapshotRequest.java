package co.cambridgetechnology.auction.core.memory.model.request.impl;

import co.cambridgetechnology.auction.core.memory.model.request.EventRequest;

public class SnapshotRequest implements EventRequest {
    private String accountId;

    public String getAccountId() {
        return accountId;
    }

    public SnapshotRequest(String accountId) {
        this.accountId = accountId;
    }

    public SnapshotRequest() {
    }
}
