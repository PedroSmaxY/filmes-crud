package com.facul.filmes.controllers;

import com.facul.filmes.domain.entities.Movie;
import com.facul.filmes.dto.movie.MovieRequestDTO;
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
    public ResponseEntity<Movie> add(@RequestBody MovieRequestDTO dto) {
        Movie movie = new Movie();
        movie.setTitle(dto.title());
        movie.setGenre(dto.genre());
        movie.setReleaseYear(dto.releaseYear());
        movie.setAvailableCopies(dto.availableCopies());
        movie.setDirector(dto.director());

        Movie newMovie = this.service.add(movie);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newMovie.getId())
                .toUri();

        return ResponseEntity.created(uri).body(newMovie);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody MovieRequestDTO dto) {

        Movie movie = new Movie();
        movie.setId(id);
        movie.setTitle(dto.title());
        movie.setGenre(dto.genre());
        movie.setReleaseYear(dto.releaseYear());
        movie.setAvailableCopies(dto.availableCopies());
        movie.setDirector(dto.director());

        this.service.update(id, movie);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/increase-copies/{copies}")
    public ResponseEntity<Void> increaseAvailableCopies(@PathVariable Long id, @PathVariable Integer copies) {
        this.service.increaseAvailableCopies(id, copies);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/decrease-copies/{copies}")
    public ResponseEntity<Void> decreaseAvailableCopies(@PathVariable Long id, @PathVariable Integer copies) {
        this.service.decreaseAvailableCopies(id, copies);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Movie movie = this.service.findMovieById(id);
        this.service.remove(movie.getId());

        return ResponseEntity.noContent().build();
    }

}
