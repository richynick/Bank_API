package com.richard.bankapi.service;

import com.richard.bankapi.payload.request.AccountRequest;
import com.richard.bankapi.payload.BankResponse;
import com.richard.bankapi.payload.request.CreditRequest;
import com.richard.bankapi.payload.request.EnquiryRequest;
import com.richard.bankapi.payload.request.TransferRequest;
import org.springframework.stereotype.Repository;

import java.io.IOException;

@Repository
public interface AccountService {
    BankResponse createAccount(AccountRequest accountDto) throws IOException;
    BankResponse balanceEnquiry(EnquiryRequest request);
    BankResponse transfer(TransferRequest transferRequest);
    BankResponse creditAccount(CreditRequest creditRequest);
}
