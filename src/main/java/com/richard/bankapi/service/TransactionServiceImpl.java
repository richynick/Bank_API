package com.richard.bankapi.service;

import com.richard.bankapi.model.Transaction;
import com.richard.bankapi.model.TransactionStatus;
import com.richard.bankapi.payload.request.TransactionRequest;
import com.richard.bankapi.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

import static com.richard.bankapi.utils.AccountUtils.billedAmount;
import static com.richard.bankapi.utils.AccountUtils.transactionFee;

@RequiredArgsConstructor
@Service
public class TransactionServiceImpl implements TransactionService{

    private final TransactionRepository transactionRepository;

    @Override
    public void saveTransaction(TransactionRequest transactionRequest) {
        Transaction transaction = Transaction.builder()
                .transactionType(transactionRequest.getTransactionType())
                .destinationAccountNumber(transactionRequest.getDestinationAccountNumber())
                .sourceAccountNumber(transactionRequest.getSourceAccountNumber())
                .amount(transactionRequest.getAmount())
                .transactionFee(transactionFee(transactionRequest.getAmount()))
                .billedAmount(billedAmount(transactionRequest.getAmount()))
                .status(TransactionStatus.PENDING)
                .build();
        transactionRepository.save(transaction);

    }
    @Scheduled(cron = "0 0 2 * * ?")
    public void analyzeTransactions(){
        List<Transaction> transactions = transactionRepository.findByStatusAndCommissionWorthy(TransactionStatus.SUCCESSFUL, false);
        for(Transaction transaction : transactions) {
            transaction.setCommissionWorthy(true);
            BigDecimal commissionfee = transactionFee(transaction.getAmount()).multiply(BigDecimal.valueOf(0.2));
            transactionRepository.save(transaction);
        }
    }
}
