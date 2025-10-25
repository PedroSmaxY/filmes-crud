package com.facul.filmes.repositories;

import com.facul.filmes.domain.entities.Rentail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RentailRepository extends JpaRepository<Rentail, Integer> {
}
