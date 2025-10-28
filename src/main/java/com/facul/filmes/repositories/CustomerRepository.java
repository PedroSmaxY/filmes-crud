package com.facul.filmes.repositories;

import com.facul.filmes.domain.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByPhoneOrEmailIgnoreCase(String phone, String email);
    Optional<Customer> findByEmailIgnoreCase(String email);
    Optional<Customer> findByPhone(String phone);
}
