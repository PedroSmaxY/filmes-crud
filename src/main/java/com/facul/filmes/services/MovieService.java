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
        movie.setAvailableCopies(movie.getAvailableCopies() + copiesToAdd);
        this.repository.save(movie);
    }

    @Transactional
    public void decreaseAvailableCopies(Long id, int copiesToDecrease) {
        var movie = this.findMovieById(id);

        if (movie.getAvailableCopies() <= 0) {
            throw new BadRequestException("No available copies to decrease.");
        }

        if (movie.getAvailableCopies() < copiesToDecrease) {
            throw new BadRequestException("Not enough available copies to decrease.");
        }
        movie.setAvailableCopies(movie.getAvailableCopies() - copiesToDecrease);
        this.repository.save(movie);
    }


    @Transactional
    public Movie add(Movie movie) {
        String title = normalizeTitle(movie.getTitle());

        this.repository.findByTitle(title).ifPresent(m -> {
            throw new DuplicateResourceException("A movie with this title already exists.");
        });

        movie.setTitle(title);
        if (movie.getAvailableCopies() == 0) {
            movie.setAvailableCopies(0);
        }

        return repository.save(movie);
    }

    @Transactional
    public void update(Long id, Movie movie) {
        var existingMovie = this.repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Movie not found."));

        String title = normalizeTitle(movie.getTitle());
        repository.findByTitle(title).ifPresent(other -> {
            if (!other.getId().equals(id)) {
                throw new DuplicateResourceException("Movie with the same title already exists.");
            }
        });

        existingMovie.setTitle(title);
        existingMovie.setGenre(movie.getGenre());
        existingMovie.setReleaseYear(movie.getReleaseYear());
        existingMovie.setAvailableCopies(movie.getAvailableCopies());
        existingMovie.setDirector(movie.getDirector());

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
}
