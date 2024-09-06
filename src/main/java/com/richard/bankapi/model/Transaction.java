package com.richard.bankapi.model;

import com.fasterxml.jackson.databind.ser.Serializers;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Transaction extends BaseEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String transactionReference;
    private BigDecimal amount;
    private BigDecimal transactionFee;
    private BigDecimal billedAmount;
    private String description;
    private String transactionType;

    @Enumerated(EnumType.STRING)
    private TransactionStatus status;

    private String statusMessage;
    private Boolean commissionWorthy;
    private BigDecimal commission;

    private String sourceAccountNumber;  // Source account
    private String destinationAccountNumber;  // Destination account
}
