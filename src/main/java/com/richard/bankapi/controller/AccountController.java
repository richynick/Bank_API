package com.richard.bankapi.controller;

import com.richard.bankapi.payload.request.AccountRequest;
import com.richard.bankapi.payload.BankResponse;
import com.richard.bankapi.payload.request.CreditRequest;
import com.richard.bankapi.payload.request.EnquiryRequest;
import com.richard.bankapi.payload.request.TransferRequest;
import com.richard.bankapi.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * Controller class for managing account-related operations in the API
 */
@RestController
@RequestMapping("/api/account")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    /**
     * Creates a new account based on the provided request data.
     *
     * @param accountDto The request object containing information for creating an account.
     * @return A ResponseEntity with BankResponse as the response body.
     */
    @PostMapping(value = "/create")
    public ResponseEntity<BankResponse> createAccount(@RequestBody AccountRequest accountDto) throws IOException {
        BankResponse bankResponse = accountService.createAccount(accountDto);
        return ResponseEntity.ok(bankResponse);
    }

    @GetMapping(value = "/balanceEnquiry")
    public ResponseEntity<BankResponse> balanceEnquiry(@RequestBody EnquiryRequest request){
        BankResponse account = accountService.balanceEnquiry(request);
        return ResponseEntity.ok(account);
    }

    @PostMapping(value = "/credit")
    public ResponseEntity<BankResponse> creditAccount(@RequestBody CreditRequest request){
        BankResponse credit = accountService.creditAccount(request);
        return ResponseEntity.ok(credit);
    }
    @PostMapping(value = "/transfer")
    public ResponseEntity<BankResponse> fundsTransfer(@RequestBody TransferRequest request){
        BankResponse transfer = accountService.transfer(request);
        return ResponseEntity.ok(transfer);
    }
}
