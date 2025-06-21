package com.example.Sorokin_Event.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public record Events(
        Long id,
        String name,
        Long ownerId,
        int maxPlaces,
        List<EventRegistration> registrationList,
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime date,
        int cost,
        int duration,
        int locationId,
        EventStatus status
)
{
}