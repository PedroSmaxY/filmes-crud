package com.facul.filmes.dto.rental;

import java.time.LocalDate;

public record RentalRequestDTO(
        Long customerId,
        Long movieId,
        Long employeeId
) { }
