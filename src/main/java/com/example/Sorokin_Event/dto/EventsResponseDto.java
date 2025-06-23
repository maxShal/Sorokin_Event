package com.example.Sorokin_Event.dto;

import com.example.Sorokin_Event.model.EventRegistration;
import com.example.Sorokin_Event.model.EventStatus;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public record EventsResponseDto(
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
