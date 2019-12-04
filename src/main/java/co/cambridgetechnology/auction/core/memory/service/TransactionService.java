package co.cambridgetechnology.auction.core.memory.service;

import co.cambridgetechnology.auction.core.memory.entity.TransactionResult;

public interface TransactionService {
    TransactionResult getLastResult();

}
