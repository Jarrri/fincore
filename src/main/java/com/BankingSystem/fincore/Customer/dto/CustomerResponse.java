package com.BankingSystem.fincore.Customer.dto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerResponse {

    private Long id;
    private String name;
    private String email;
    private String status;

    // getters and setters
}
