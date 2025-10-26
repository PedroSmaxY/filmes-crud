package com.facul.filmes.services;

import com.facul.filmes.domain.entities.Movie;
import com.facul.filmes.repositories.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {
    private final MovieRepository repository;

    public  MovieService(MovieRepository movieRepository) {
        this.repository = movieRepository;
    }

    public List<Movie> listAllMovies() {
        return repository.findAll();
    }

    public Movie add(Movie movie) {
        return repository.save(movie);
    }

    public void remove(Long id) {
        repository.deleteById(id);
    }
}
