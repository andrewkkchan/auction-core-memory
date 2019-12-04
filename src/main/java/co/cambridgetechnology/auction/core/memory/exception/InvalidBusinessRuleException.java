package co.cambridgetechnology.auction.core.memory.exception;

import co.cambridgetechnology.auction.core.memory.entity.TransactionEvent;

/**
 * Run Time Exception thrown during processing of one {@link TransactionEvent}
 */
public class InvalidBusinessRuleException extends RuntimeException {
    public InvalidBusinessRuleException(String message) {
        super(message);
    }
}
