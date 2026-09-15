package com.BankingSystem.fincore.Account;

import com.BankingSystem.fincore.Account.dto.AccountResponse;
import com.BankingSystem.fincore.Account.dto.CreateAccountRequest;
import com.BankingSystem.fincore.Account.dto.DepositRequest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping
    public ResponseEntity<AccountResponse> createAccount(
            @Valid @RequestBody CreateAccountRequest request) {

        AccountResponse response = accountService.createAccount(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PostMapping("/{accountNumber}/deposit")
    public ResponseEntity<AccountResponse> deposit(
            @PathVariable String accountNumber,
            @Valid @RequestBody DepositRequest request) {

        AccountResponse response = accountService.deposit(accountNumber, request);

        return ResponseEntity.ok(response);
    }
    @PostMapping("/{accountNumber}/withdraw")
    public ResponseEntity<AccountResponse> withdraw(
            @PathVariable String accountNumber,
            @Valid @RequestBody DepositRequest request) {

        AccountResponse response = accountService.withdraw(accountNumber, request);

        return ResponseEntity.ok(response);
    }
}