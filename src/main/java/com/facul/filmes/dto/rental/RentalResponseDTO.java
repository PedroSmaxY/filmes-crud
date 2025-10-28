package com.facul.filmes.dto.rental;

import com.facul.filmes.domain.enums.RentalStatus;

import java.time.LocalDate;

public record RentalResponseDTO(
        Long id,
        Long movieId,
        Long customerId,
        Long employeeId,
        LocalDate rentalDate,
        LocalDate returnDate,
        RentalStatus status
) {
}
