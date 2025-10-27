package com.facul.filmes.controllers;

import com.facul.filmes.domain.entities.Customer;
import com.facul.filmes.repositories.CustomerRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/employees/customers")
public class EmployeeCustomerController {

    private final CustomerRepository repository;

    public EmployeeCustomerController(CustomerRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Customer> listCustomers() {
        return repository.findAll();
    }
}
