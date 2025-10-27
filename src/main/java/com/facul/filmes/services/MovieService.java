package com.facul.filmes.services;

import com.facul.filmes.domain.entities.Movie;
import com.facul.filmes.repositories.MovieRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MovieService {
    private final MovieRepository repository;

    public MovieService(MovieRepository movieRepository) {
        this.repository = movieRepository;
    }

    public List<Movie> listAllMovies() {
        return repository.findAll();
    }

    public Movie findMovieById(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Movie not found."));
    }

    public Movie add(Movie movie) {
        var existingMovie = repository.findByTitle(movie.getTitle());

        if (existingMovie.isPresent()) {
            throw new RuntimeException("Movie with the same title already exists.");
        }

        return repository.save(movie);
    }

    @Transactional
    public Movie update(Movie movie) {
        var existingMovie = repository.findById(movie.getId());
        if (existingMovie.isEmpty()) {
            throw new RuntimeException("Movie not found.");
        }
        var movieToUpdate = existingMovie.get();

        movieToUpdate.setTitle(movie.getTitle());
        movieToUpdate.setGenre(movie.getGenre());
        movieToUpdate.setReleaseYear(movie.getReleaseYear());
        movieToUpdate.setAvailableCopies(movie.getAvailableCopies());
        movieToUpdate.setDirector(movie.getDirector());

        return repository.save(movieToUpdate);

    }

    public void remove(Long id) {
        var existingMovie = repository.findById(id);

        if (existingMovie.isEmpty()) {
            throw new RuntimeException("Movie not found.");
        }

        repository.deleteById(id);
    }
}
