package com.BankingSystem.fincore.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import com.BankingSystem.fincore.Customer.dto.CreateCustomerRequest;
import com.BankingSystem.fincore.Customer.dto.CustomerResponse;
@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerResponse createCustomer(CreateCustomerRequest request) {

        if (customerRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        Customer customer = new Customer();

        customer.setName(request.getName());
        customer.setEmail(request.getEmail());
        customer.setPassword(request.getPassword());
        customer.setStatus("ACTIVE");
        customer.setCreatedAt(LocalDateTime.now());

        Customer saved = customerRepository.save(customer);

        CustomerResponse response = new CustomerResponse();

        response.setId(saved.getId());
        response.setName(saved.getName());
        response.setEmail(saved.getEmail());
        response.setStatus(saved.getStatus());

        return response;
    }
    public CustomerResponse getCustomer(Long id) {

        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        CustomerResponse response = new CustomerResponse();
        response.setId(customer.getId());
        response.setName(customer.getName());
        response.setEmail(customer.getEmail());
        response.setStatus(customer.getStatus());

        return response;
    }
}