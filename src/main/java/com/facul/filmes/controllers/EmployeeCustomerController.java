package com.facul.filmes.controllers;

import com.facul.filmes.domain.entities.Customer;
import com.facul.filmes.dto.customer.CustomerRequestDTO;
import com.facul.filmes.services.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/employees/customers")
public class EmployeeCustomerController {

    private final CustomerService customerService;

    public EmployeeCustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public List<Customer> listCustomers() {
        return this.customerService.findAll();
    }

    @GetMapping("/{customerId}")
    public Customer findCustomerById(@PathVariable Long customerId) {
        return customerService.findById(customerId);
    }

    @GetMapping("/email/{email}")
    public Customer findCustomerByEmail(@PathVariable String email) {
        return customerService.findByEmail(email);
    }

    @GetMapping("/phone/{phone}")
    public Customer findCustomerByPhone(@PathVariable String phone) {
        return customerService.findByPhone(phone);
    }

    @PostMapping
    public ResponseEntity<Customer> createCustomer(@RequestBody CustomerRequestDTO dto) {
        Customer customer = new Customer();
        customer.setName(dto.name());
        customer.setEmail(dto.email());
        customer.setPhone(dto.phone());

        Customer newCustomer = this.customerService.save(customer);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newCustomer.getId())
                .toUri();

        return ResponseEntity.created(uri).body(newCustomer);
    }

    @PutMapping("/{customerId}")
    public ResponseEntity<Void> updateCustomer(@PathVariable Long customerId, @RequestBody CustomerRequestDTO dto) {
        Customer customer = new Customer();
        customer.setName(dto.name());
        customer.setEmail(dto.email());
        customer.setPhone(dto.phone());

        this.customerService.update(customerId, customer);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{customerId}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long customerId) {
        this.customerService.deleteById(customerId);
        return ResponseEntity.noContent().build();
    }
}
