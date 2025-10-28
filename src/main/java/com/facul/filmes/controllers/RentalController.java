package com.facul.filmes.controllers;

import com.facul.filmes.domain.entities.Rental;
import com.facul.filmes.dto.rental.RentalRequestDTO;
import com.facul.filmes.dto.rental.RentalResponseDTO;
import com.facul.filmes.services.RentalService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/rentals")
public class RentalController {
    private final RentalService rentalService;

    public RentalController(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    @GetMapping
    public ResponseEntity<List<RentalResponseDTO>> getAllRentals() {
        var rentals = this.rentalService.findAll()
                .stream()
                .map(r -> new RentalResponseDTO(
                        r.getId(),
                        r.getMovie().getId(),
                        r.getCustomer().getId(),
                        r.getProcessedBy().getId(),
                        r.getRentalDate(),
                        r.getReturnDate(),
                        r.getStatus()
                )).toList();

        return ResponseEntity.ok(rentals);
    }

    @PostMapping
    public ResponseEntity<RentalResponseDTO> createRental(@RequestBody RentalRequestDTO dto) {
        var rental = this.rentalService.createRental(dto.movieId(), dto.customerId(), dto.employeeId());

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(rental.getId())
                .toUri();

        RentalResponseDTO rentalResponseDTO = new RentalResponseDTO(
                rental.getId(),
                rental.getMovie().getId(),
                rental.getCustomer().getId(),
                rental.getProcessedBy().getId(),
                rental.getRentalDate(),
                rental.getReturnDate(),
                rental.getStatus()
        );

        return ResponseEntity.created(uri).body(rentalResponseDTO);
    }

    @PatchMapping("/{rentalId}/return")
    public ResponseEntity<Rental> returnRental(@PathVariable Long rentalId) {
        var rental = this.rentalService.returnRental(rentalId);
        return ResponseEntity.ok(rental);
    }
}
