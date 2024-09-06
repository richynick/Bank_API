package com.richard.bankapi.model;

public enum TransactionStatus {

    SUCCESSFUL,          // Transaction completed successfully
    INSUFFICIENT_FUNDS,  // Transaction failed due to insufficient funds
    FAILED,              // Transaction failed for other reasons
    PENDING,             // Transaction is pending and not yet completed
    CANCELLED            // Transaction was cancelled by the user or system
}
