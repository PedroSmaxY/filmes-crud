package com.facul.filmes.dto.customer;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record CustomerRequestDTO(

        @NotBlank
        @Pattern(
                regexp = "^[A-Za-zÀ-ÖØ-öø-ÿ\\s]+$",
                message = "O nome deve conter apenas letras e espaços"
        )
        String name,

        @Email
        String email,

        @NotBlank
        @Pattern(
                regexp = "^\\(\\d{2}\\)\\s9?\\d{4}-\\d{4}$",
                message = "O telefone deve estar no formato (XX) 9XXXX-XXXX"
        )
        String phone

) { }