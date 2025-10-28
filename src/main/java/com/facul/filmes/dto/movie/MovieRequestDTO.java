package com.facul.filmes.dto.movie;

public record MovieRequestDTO(
        String title,
        String genre,
        Integer releaseYear,
        Integer availableCopies,
        String director
) { }
