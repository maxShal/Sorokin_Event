package com.example.Sorokin_Event.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.List;

public record Event(
        Long id,
        String name,
        Long ownerId,
        Integer maxPlaces,
        List<EventRegistration> registrationList,
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime date,
        Integer cost,
        Integer duration,
        Long locationId,
        EventStatus status
)
{
}