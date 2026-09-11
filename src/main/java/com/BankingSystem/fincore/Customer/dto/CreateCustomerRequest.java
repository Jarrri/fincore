package com.BankingSystem.fincore.Customer.dto;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class CreateCustomerRequest {

    private String name;
    private String email;
    private String password;

    // getters and setters
}
