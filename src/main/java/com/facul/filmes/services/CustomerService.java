package com.facul.filmes.services;

import com.facul.filmes.domain.entities.Customer;
import com.facul.filmes.repositories.CustomerRepository;
import com.facul.filmes.services.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer findById(Integer id) {
        return customerRepository.findById(id.longValue())
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id " + id));
    }

    public Customer save(Customer customer) {

        return customerRepository.save(customer);
    }
}
