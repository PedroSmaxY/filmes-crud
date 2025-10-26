package com.facul.filmes.repositories;

import com.facul.filmes.domain.entities.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    Integer id(Long id);
}
