package com.facul.filmes.services;

import com.facul.filmes.domain.entities.Customer;
import com.facul.filmes.repositories.CustomerRepository;
import com.facul.filmes.services.exceptions.DuplicateResourceException;
import com.facul.filmes.services.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CustomerService {
    CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> findAll() {
        return this.customerRepository.findAll();
    }

    public Customer findById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id " + id));
    }

    public Customer findByEmail(String email) {
        return customerRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with email " + email));
    }

    public Customer findByPhone(String phone) {
        return customerRepository.findByPhone(phone)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with phone " + phone));
    }

    @Transactional
    public Customer save(Customer customer) {
        this.customerRepository
                .findByPhoneOrEmailIgnoreCase(customer.getPhone(), customer.getEmail())
                .ifPresent(c -> { throw new DuplicateResourceException("Customer with the same phone or email already exists."); });

        return customerRepository.save(customer);
    }

    @Transactional
    public void update(Long id, Customer customer) {
        Customer existingCustomer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id " + id));

        this.customerRepository
                .findByPhoneOrEmailIgnoreCase(customer.getPhone(), customer.getEmail())
                .ifPresent(c -> {
                    if (!c.getId().equals(id)) {
                        throw new DuplicateResourceException("Another customer with the same phone or email already exists.");
                    }
                });

        existingCustomer.setName(customer.getName());
        existingCustomer.setEmail(customer.getEmail());
        existingCustomer.setPhone(customer.getPhone());

        customerRepository.save(existingCustomer);
    }

    @Transactional
    public void deleteById(Long id) {
        if (!customerRepository.existsById(id)) {
            throw new ResourceNotFoundException("Customer not found with id " + id);
        }
        customerRepository.deleteById(id);
    }
}
