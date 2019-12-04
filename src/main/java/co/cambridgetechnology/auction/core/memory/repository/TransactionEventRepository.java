package co.cambridgetechnology.auction.core.memory.repository;

import co.cambridgetechnology.auction.core.memory.entity.TransactionEvent;

import java.util.Optional;

public interface TransactionEventRepository  {
    Iterable<TransactionEvent> findAll();

    Optional<TransactionEvent> findById(String id);

    boolean existsById(String id);

    TransactionEvent save(TransactionEvent transactionEvent);
}
