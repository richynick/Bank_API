package com.richard.bankapi.payload.request;

import com.richard.bankapi.model.TransactionStatus;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionRequest {

    private BigDecimal amount;
    private String description;
    private TransactionStatus status;
    private String transactionType;

    private String statusMessage;
    private Boolean commissionWorthy;
    private Double commission;

    private String sourceAccountNumber;  // Source account
    private String destinationAccountNumber;
}
