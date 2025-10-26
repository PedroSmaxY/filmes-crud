package com.facul.filmes.controllers;

import com.facul.filmes.domain.entities.Movie;
import com.facul.filmes.services.MovieService;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    public Movie add(@RequestBody Movie movie) {
        return service.add(movie);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.remove(id);
    }

}
