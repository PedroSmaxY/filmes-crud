package com.facul.filmes.repositories;

import com.facul.filmes.domain.entities.Rental;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RentalRepository extends JpaRepository<Rental, Long> {
}
