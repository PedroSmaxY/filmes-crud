package com.facul.filmes.services;

import com.facul.filmes.domain.entities.Movie;
import com.facul.filmes.repositories.MovieRepository;
import com.facul.filmes.services.exceptions.BadRequestException;
import com.facul.filmes.services.exceptions.DuplicateResourceException;
import com.facul.filmes.services.exceptions.ResourceNotFoundException;
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
        return this.repository.findAll();
    }

    public Movie findMovieById(Long id) {
        return this.repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Movie not found."));
    }

    @Transactional
    public void increaseAvailableCopies(Long id, int copiesToAdd) {
        var movie = this.findMovieById(id);
        movie.setAvailableCopies((movie.getAvailableCopies() == null ? 0 : movie.getAvailableCopies()) + copiesToAdd);
        this.repository.save(movie);
    }

    @Transactional
    public void decreaseAvailableCopies(Long id, int copiesToDecrease) {
        var movie = this.findMovieById(id);
        int current = movie.getAvailableCopies() == null ? 0 : movie.getAvailableCopies();

        if (current <= 0) {
            throw new BadRequestException("No available copies to decrease.");
        }
        if (current < copiesToDecrease) {
            throw new BadRequestException("Not enough available copies to decrease.");
        }

        movie.setAvailableCopies(current - copiesToDecrease);
        this.repository.save(movie);
    }

    @Transactional
    public Movie add(Movie movie) {
        String title = normalizeTitle(movie.getTitle());
        String director = normalizeDirector(movie.getDirector());

        this.repository.findByTitleIgnoreCaseAndDirectorIgnoreCase(title, director).ifPresent(m -> {
            throw new DuplicateResourceException("A movie with this title and director already exists.");
        });

        movie.setTitle(title);
        movie.setDirector(director);
        if (movie.getAvailableCopies() == null) {
            movie.setAvailableCopies(0);
        }
        return repository.save(movie);
    }

    @Transactional
    public void update(Long id, Movie movie) {
        var existingMovie = this.repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Movie not found."));

        String title = normalizeTitle(movie.getTitle());
        String director = normalizeDirector(movie.getDirector());

        repository.findByTitleIgnoreCaseAndDirectorIgnoreCase(title, director).ifPresent(other -> {
            if (!other.getId().equals(id)) {
                throw new DuplicateResourceException("A movie with this title and director already exists.");
            }
        });

        existingMovie.setTitle(title);
        existingMovie.setGenre(movie.getGenre());
        existingMovie.setReleaseYear(movie.getReleaseYear());
        existingMovie.setAvailableCopies(movie.getAvailableCopies());
        existingMovie.setDirector(director);

        this.repository.save(existingMovie);
    }

    @Transactional
    public void remove(Long id) {
        this.repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Movie not found."));
        repository.deleteById(id);
    }

    private String normalizeTitle(String title) {
        if (title == null || title.isBlank()) {
            throw new BadRequestException("Title is required.");
        }
        return title.trim();
    }

    private String normalizeDirector(String director) {
        if (director == null || director.isBlank()) {
            throw new BadRequestException("Director is required.");
        }
        return director.trim();
    }
}