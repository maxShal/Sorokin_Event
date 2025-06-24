package com.example.Sorokin_Event.dto;

import com.example.Sorokin_Event.model.EventStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

public record EventUpdateDto(
        @Size(min = 1, max = 100)
        String name,
        @Min(1)
        Integer maxPlaces,
        @Future
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime date,
        @Min(500)
        Integer cost,
        Integer duration,
        Long locationId
) {
}
