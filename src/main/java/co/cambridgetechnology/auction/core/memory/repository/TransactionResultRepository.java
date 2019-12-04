package co.cambridgetechnology.auction.core.memory.repository;

import co.cambridgetechnology.auction.core.memory.entity.TransactionResult;

import java.util.Optional;

public interface TransactionResultRepository {
    Optional<TransactionResult> findByRequestId(String requestId);

    Iterable<TransactionResult> findAll();

    TransactionResult save(TransactionResult transactionResult);
}
