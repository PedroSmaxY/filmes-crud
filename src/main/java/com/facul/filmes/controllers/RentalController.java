package com.facul.filmes.controllers;

import com.facul.filmes.domain.entities.Rental;
import com.facul.filmes.dto.rental.RentalRequestDTO;
import com.facul.filmes.services.RentalService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/rentals")
public class RentalController {
    private final RentalService rentalService;

    public RentalController(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    @PostMapping
    public ResponseEntity<Rental> createRental(@RequestBody RentalRequestDTO dto) {
        var rental = this.rentalService.createRental(dto.movieId(), dto.customerId(), dto.employeeId());

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(rental.getId())
                .toUri();

        return ResponseEntity.created(uri).body(rental);
    }

    @PatchMapping("/{rentalId}/return")
    public ResponseEntity<Rental> returnRental(@PathVariable Long rentalId) {
        var rental = this.rentalService.returnRental(rentalId);
        return ResponseEntity.ok(rental);
    }
}
