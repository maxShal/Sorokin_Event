package com.example.Sorokin_Event.dto;

import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

public record EventResponseDto(
        @NotBlank
        @Size(min = 1, max = 100)
        String name,
        @NotNull
        @Min(1)
        int maxPlaces,
        @Future
        LocalDateTime date,
        @NotNull
        @Min(500)
        int cost,
        @NotNull
        int duration,
        @NotNull
        Long locationId
) {
}
