package com.richard.bankapi.utils;

import java.math.BigDecimal;
import java.time.Year;

public class AccountUtils {

    public static final String REQUEST_SUCCESSFUL = "00";
    public static final String ERROR_CODE = "01";

    public static final String ACCOUNT_EXISTS_CODE ="002";
    public static final String ACCOUNT_FOUND_CODE ="009";
    public static final String ACCOUNT_FOUND_MESSAGE ="Account has been found";
    public static final String ACCOUNT_EXISTS_MESSAGE ="A user with this email already exists";
    public static final String REQUEST_FAILED = "99";
    public static final String ERROR = "ERROR";
    public static final String SUCCESS = "SUCCESS";
    public static final String ACCOUNT_NOT_EXIST_CODE =  "003";
    public static final String ACCOUNT_NOT_EXIST_MESSAGE =  "Account number provided does not exist";
    public static final String DEBIT_ACCOUNT_NOT_EXIST_CODE =  "Account number provided does not exist";
    public static final String INSUFFICIENT_BALANCE_CODE ="007";
    public static final String INSUFFICIENT_BALANCE_MESSAGE ="Insufficient balance";
    public static final String TRANSFER_SUCCESSFUL_CODE ="008";
    public static final String TRANSFER_SUCCESSFUL_MESSAGE ="Transfer Successful";

    /**
     * generated account number will have 2024 + randomSixDigits
     * @return
     */
    public static String generateAccountNumber(){
        Year currentYear = Year.now();
        int min = 100000;
        int max = 999999;

//     generate a random number between min and max
        int randNumber = (int  ) Math.floor(Math.random() * (max - min + 1) + min);
//     convert the current randomNumber to strings, then concatenated with currentYear

        String year = String.valueOf(currentYear);
        String randomNumber =  String.valueOf(randNumber);
        StringBuilder accountNumber = new StringBuilder();
        return accountNumber.append(year).append(randomNumber).toString() ;

    }

    public static BigDecimal transactionFee(BigDecimal amount){
        return (amount.multiply(BigDecimal.valueOf(0.5)));
    }
    public static BigDecimal billedAmount(BigDecimal amount){
        return (transactionFee(amount).add(amount));
    }
}
