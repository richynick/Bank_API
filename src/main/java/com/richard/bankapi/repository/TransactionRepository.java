package com.richard.bankapi.repository;

import com.richard.bankapi.model.Transaction;
import com.richard.bankapi.model.TransactionStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByStatusAndCommissionWorthy(TransactionStatus status, Boolean commissionWorthy);
}
