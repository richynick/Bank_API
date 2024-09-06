package com.richard.bankapi.repository;

import com.richard.bankapi.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Boolean existsByEmail(String email);
    Boolean existsByAccountNumber(String accountNumber);
    Account findByAccountNumber(String accountNumber);

}
