package com.example.Sorokin_Event.controller;


import com.example.Sorokin_Event.dto.EventDto;
import com.example.Sorokin_Event.mapper.EventMapper;
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

    private final EventMapper mapper;

    public RegistrationController(RegistrationService service, EventMapper mapper) {
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
    public ResponseEntity<List<EventDto>> getAllRegistrations()
    {
        var foundEvents = service.getAllRegistrations();
        return new ResponseEntity<>(foundEvents.stream().map(
                mapper::toDto)
                .toList(),
                HttpStatus.OK
        );
    }
}
