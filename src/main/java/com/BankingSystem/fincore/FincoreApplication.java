package com.BankingSystem.fincore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FincoreApplication {

	public static void main(String[] args) {
		System.out.println("Fincore Application started.");
		SpringApplication.run(FincoreApplication.class, args);
	}

}
