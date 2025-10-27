package com.facul.filmes.repositories;

import com.facul.filmes.domain.entities.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    Optional<Movie> findByTitle(String title);
    Optional<Movie> findByTitleIgnoreCaseAndDirectorIgnoreCase(String title, String director);
}
