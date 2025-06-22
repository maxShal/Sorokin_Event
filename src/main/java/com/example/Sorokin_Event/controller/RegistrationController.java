package com.example.Sorokin_Event.controller;


import com.example.Sorokin_Event.dto.EventsDto;
import com.example.Sorokin_Event.entity.EventRegistrationEntity;
import com.example.Sorokin_Event.mapper.EventsMapper;
import com.example.Sorokin_Event.service.RegistrationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/events/registrations")
public class RegistrationController
{
    private final RegistrationService service;

    private final EventsMapper mapper;

    public RegistrationController(RegistrationService service, EventsMapper mapper) {
        this.service = service;

        this.mapper = mapper;
    }

    @PostMapping("/{eventId}")
    public ResponseEntity<Void> registrationOnEvent(@PathVariable Long eventId)
    {
        service.registrationOnEvent(eventId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/cansel/{eventId}")
    public ResponseEntity<Void> deleteRegistrationOnEvent(@PathVariable Long eventId)
    {
        service.deleteRegistrationOnEvent(eventId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/my")
    public ResponseEntity<List<EventsDto>> getAllRegistrations()
    {
        var foundEvents = service.getAllRegistrations();
        return new ResponseEntity<>(foundEvents.stream().map(
                mapper::toDto)
                .toList(),
                HttpStatus.OK
        );
    }
}
