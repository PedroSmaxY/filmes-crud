package com.facul.filmes.repositories;

import com.facul.filmes.domain.entities.Customer;
import com.facul.filmes.domain.entities.Rental;
import com.facul.filmes.domain.enums.RentalStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RentalRepository extends JpaRepository<Rental, Long> {
    Boolean existsByCustomerIdAndMovieIdAndStatus(Long customerId, Long movie_id, RentalStatus status);
}
