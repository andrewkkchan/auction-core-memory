package co.cambridgetechnology.auction.core.memory.consumer;


import co.cambridgetechnology.auction.core.memory.entity.TransactionEvent;
import co.cambridgetechnology.auction.core.memory.entity.Type;

/**
 * Processor to process {@link TransactionEvent} in a strict serial order
 * Can make use of {@link Producer} to produce result as a side effect of processing
 */
public interface Processor {
    /**
     * Process one {@link TransactionEvent} in strictly serial order
     *
     * @param transactionEvent {@link TransactionEvent} to be processed
     */
    void process(TransactionEvent transactionEvent);

    /**
     * Provide {@link Type} for {@link Consumer} to rightly pick the responsible {@link Processor}
     *
     * @return {@link Type} to String
     */
    String getType();

}
