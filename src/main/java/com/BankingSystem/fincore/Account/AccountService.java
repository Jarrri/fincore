package com.BankingSystem.fincore.Account;

import com.BankingSystem.fincore.Account.dto.AccountResponse;
import com.BankingSystem.fincore.Account.dto.CreateAccountRequest;
import com.BankingSystem.fincore.Account.dto.DepositRequest;
import com.BankingSystem.fincore.Customer.Customer;
import com.BankingSystem.fincore.Customer.CustomerRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.BankingSystem.fincore.Account.dto.DepositRequest;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;

    public AccountResponse createAccount(CreateAccountRequest request) {

        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        Account account = new Account();

        account.setCustomer(customer);
        account.setAccountNumber(generateAccountNumber());
        account.setAccountType(request.getAccountType());
        account.setBalance(BigDecimal.ZERO);
        account.setCurrency(request.getCurrency());
        account.setStatus("ACTIVE");
        account.setCreatedAt(java.time.LocalDateTime.now());

        Account savedAccount = accountRepository.save(account);

        return mapToResponse(savedAccount);
    }

    private String generateAccountNumber() {
        return String.valueOf(10000000L + System.currentTimeMillis() % 90000000L);
    }

    private AccountResponse mapToResponse(Account account) {

        AccountResponse response = new AccountResponse();

        response.setId(account.getId());
        response.setCustomerId(account.getCustomer().getId());
        response.setAccountNumber(account.getAccountNumber());
        response.setAccountType(account.getAccountType());
        response.setBalance(account.getBalance());
        response.setCurrency(account.getCurrency());
        response.setStatus(account.getStatus());

        return response;
    }
    @Transactional
public AccountResponse deposit(String accountNumber, DepositRequest request) {

    Account account = accountRepository.findByAccountNumber(accountNumber)
            .orElseThrow(() -> new RuntimeException("Account not found"));

    if (!account.getStatus().equals("ACTIVE")) {
        throw new RuntimeException("Account is not active");
    }

    if (!account.getCurrency().equals(request.getCurrency())) {
        throw new RuntimeException("Currency mismatch");
    }

    account.setBalance(account.getBalance().add(request.getAmount()));

    Account savedAccount = accountRepository.save(account);

    return mapToResponse(savedAccount);
}
@Transactional
public AccountResponse withdraw(String accountNumber, DepositRequest request) {

    Account account = accountRepository.findByAccountNumber(accountNumber)
            .orElseThrow(() -> new RuntimeException("Account not found"));

    if (!account.getStatus().equals("ACTIVE")) {
        throw new RuntimeException("Account is not active");
    }

    if (!account.getCurrency().equals(request.getCurrency())) {
        throw new RuntimeException("Currency mismatch");
    }

    if (account.getBalance().compareTo(request.getAmount()) < 0) {
        throw new RuntimeException("Insufficient balance");
    }

    account.setBalance(
            account.getBalance().subtract(request.getAmount())
    );

    Account savedAccount = accountRepository.save(account);

    return mapToResponse(savedAccount);
}
}