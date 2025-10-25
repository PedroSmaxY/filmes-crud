package com.facul.filmes.domain.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "rentail")
public class Rentail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "movie_id")
    private Movie movie;

    private LocalDate rentalDate;
    private LocalDate returnDate;
}
