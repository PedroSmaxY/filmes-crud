package com.facul.filmes.domain.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "movie")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String genre;
    private Integer release_year;
    private String director;
}
