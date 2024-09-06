package com.richard.bankapi.service;

import com.richard.bankapi.model.TransactionStatus;
import com.richard.bankapi.payload.request.*;
import com.richard.bankapi.payload.AccountInfo;
import com.richard.bankapi.payload.BankResponse;
import com.richard.bankapi.model.Account;
import com.richard.bankapi.repository.AccountRepository;
import com.richard.bankapi.utils.AccountUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;

@RequiredArgsConstructor
@Service
public class AccountServiceImpl implements AccountService{

    private final AccountRepository accountRepository;
    private final TransactionService transactionService;

    /**
     * creating a new account
     *
     */
    @Override
    public BankResponse createAccount(AccountRequest accountDto) throws IOException {
        if(accountRepository.existsByEmail(accountDto.getEmail())){
            return BankResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_EXISTS_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_EXISTS_MESSAGE)
                    .accountInfo(null)
                    .build();
        }

        Account newAccount = Account.builder()
                .fullName(accountDto.getFullName())
                .email(accountDto.getEmail())
                .bvn(accountDto.getBvn())
                .phoneNumber(accountDto.getPhoneNumber())
                .address(accountDto.getAddress())
                .accountNumber(AccountUtils.generateAccountNumber())
                .accountBalance(BigDecimal.ZERO)
                .build();
        Account savedAccount = accountRepository.save(newAccount);
        return BankResponse.builder()
                .responseCode(AccountUtils.REQUEST_SUCCESSFUL)
                .responseMessage(AccountUtils.SUCCESS)
                .accountInfo(AccountInfo.builder()
                        .accountName(savedAccount.getFullName())
                        .accountBalance(savedAccount.getAccountBalance())
                        .accountNumber(savedAccount.getAccountNumber())
                        .build())
                .build();
    }

    @Override
    public BankResponse balanceEnquiry(EnquiryRequest request) {
        /**
         *
         *
         */
        boolean isAccountExit = accountRepository.existsByAccountNumber(request.getAccountNumber());
        if(!isAccountExit){
            return BankResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_NOT_EXIST_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_EXISTS_MESSAGE)
                    .accountInfo(null)
                    .build();
        }
        Account foundAccount = accountRepository.findByAccountNumber(request.getAccountNumber());
        return BankResponse.builder()
                .responseCode(AccountUtils.ACCOUNT_FOUND_CODE)
                .responseMessage(AccountUtils.ACCOUNT_FOUND_MESSAGE)
                .accountInfo(AccountInfo.builder()
                        .accountBalance(foundAccount.getAccountBalance())
                        .accountName(foundAccount.getFullName())
                        .accountNumber(foundAccount.getAccountNumber())
                        .build())
                .build();
    }

    @Override
    public BankResponse transfer(TransferRequest transferRequest) {

        /**
         * Get the account to debit
         * Check if amount debit is not more than balance
         * debit account
         * get the account to credit
         */

        boolean isSourceAccountExist = accountRepository.existsByAccountNumber(transferRequest.getSourceAccountNumber());
        boolean isDestinationExist = accountRepository.existsByAccountNumber(transferRequest.getDestinationAccountNumber());

        if(!isDestinationExist){
            return BankResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_NOT_EXIST_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_EXISTS_MESSAGE)
                    .accountInfo(null)
                    .build();
        }
       Account sourceAccount = accountRepository.findByAccountNumber(transferRequest.getSourceAccountNumber());
        if(transferRequest.getAmount().compareTo(sourceAccount.getAccountBalance()) > 0){
            return BankResponse.builder()
                    .responseCode(AccountUtils.INSUFFICIENT_BALANCE_CODE)
                    .responseMessage(AccountUtils.INSUFFICIENT_BALANCE_MESSAGE)
                    .accountInfo(null)
                    .build();
        }
        sourceAccount.setAccountBalance(sourceAccount.getAccountBalance().subtract(transferRequest.getAmount()));
        Account savedSourceAccount= accountRepository.save(sourceAccount);

        Account destinationAccount = accountRepository.findByAccountNumber(transferRequest.getDestinationAccountNumber());
        destinationAccount.setAccountBalance(destinationAccount.getAccountBalance().add(transferRequest.getAmount()));
        accountRepository.save(destinationAccount);

        TransactionRequest transactionRequest = TransactionRequest.builder()
                .amount(transferRequest.getAmount())
                .sourceAccountNumber(transferRequest.getSourceAccountNumber())
                .destinationAccountNumber(transferRequest.getDestinationAccountNumber())
                .status(TransactionStatus.SUCCESSFUL)
                .transactionType("transfer")
                .build();

        transactionService.saveTransaction(transactionRequest);

        return BankResponse.builder()
                .responseCode(AccountUtils.TRANSFER_SUCCESSFUL_CODE)
                .responseMessage(AccountUtils.TRANSFER_SUCCESSFUL_MESSAGE)
                .accountInfo(null)
                .build();
    }

    @Override
    public BankResponse creditAccount(CreditRequest creditRequest) {
        boolean isAccountExit = accountRepository.existsByAccountNumber(creditRequest.getAccountNumber());
        if(!isAccountExit){
            return BankResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_NOT_EXIST_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_EXISTS_MESSAGE)
                    .accountInfo(null)
                    .build();
        }
        Account accountToCredit = accountRepository.findByAccountNumber(creditRequest.getAccountNumber());
        accountToCredit.setAccountBalance(accountToCredit.getAccountBalance().add(creditRequest.getAmount()));
        accountRepository.save(accountToCredit);
        return BankResponse.builder()
                .responseCode(AccountUtils.REQUEST_SUCCESSFUL)
                .responseMessage(AccountUtils.SUCCESS)
                .accountInfo(AccountInfo.builder()
                        .accountNumber(accountToCredit.getAccountNumber())
                        .accountName(accountToCredit.getFullName())
                        .accountBalance(accountToCredit.getAccountBalance())
                        .build())
                .build();
    }
}
