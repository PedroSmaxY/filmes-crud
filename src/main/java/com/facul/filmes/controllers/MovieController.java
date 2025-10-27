package com.facul.filmes.controllers;

import com.facul.filmes.domain.entities.Movie;
import com.facul.filmes.services.MovieService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/movies")
public class MovieController {
    private final MovieService service;

    public MovieController(MovieService service) {
        this.service = service;
    }

    @GetMapping
    public List<Movie> listAll() {
        return service.listAllMovies();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movie> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findMovieById(id));
    }

    @PostMapping
    public ResponseEntity<Movie> add(@RequestBody Movie movie) {
        Movie newMovie = this.service.add(movie);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(newMovie.getId()).toUri();

        return ResponseEntity.created(uri).body(newMovie);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Movie movie = this.service.findMovieById(id);
        this.service.remove(movie.getId());

        return ResponseEntity.noContent().build();
    }

}
