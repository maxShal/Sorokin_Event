package com.example.Sorokin_Event.dto;

import com.example.Sorokin_Event.model.EventStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

public record EventDto(
            @NotBlank
            @Size(min = 1, max = 100)
            String name,
            Long ownerId,
            @NotNull
            @Min(1)
            int maxPlaces,
            @PositiveOrZero
            int occupiedPlaces,
            @Future
            @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
            LocalDateTime date,
            @NotNull
            @Min(500)
            int cost,
            @NotNull
            int duration,
            Long locationId,
            EventStatus status
) {
}
