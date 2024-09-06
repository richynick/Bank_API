package com.richard.bankapi.service;

import com.richard.bankapi.model.Transaction;
import com.richard.bankapi.payload.request.TransactionRequest;

public interface TransactionService {

    void saveTransaction(TransactionRequest transactionRequest);
}
