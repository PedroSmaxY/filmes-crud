package com.facul.filmes.services;

import com.facul.filmes.domain.entities.Rental;
import com.facul.filmes.repositories.CustomerRepository;
import org.springframework.stereotype.Service;

@Service
public class RentalService {
    private final MovieService movieService;
    private final CustomerRepository customerRepository;

    public RentalService(MovieService movieService, CustomerRepository customerRepository) {
        this.movieService = movieService;
        this.customerRepository = customerRepository;
    }

    public Rental createRental(Rental rental) {
        // Decrease available copies of the movie
        movieService.decreaseAvailableCopies(rental.getMovie().getId(), 1);

        // Additional logic for creating a rental can be added here

        return rental;
    }
}
