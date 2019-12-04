package co.cambridgetechnology.auction.core.memory.entity;

import co.cambridgetechnology.auction.core.memory.entity.TransactionEvent;

/**
 * Type of {@link TransactionEvent} allowed for the Ledger to process
 */
public enum Type {
    CREATE_ACCOUNT("create-account"),
    BACK_UP("back-up"),
    OFFER("offer"),
    BID("bid"),
    ACCEPTANCE("acceptance");

    private final String text;

    Type(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
