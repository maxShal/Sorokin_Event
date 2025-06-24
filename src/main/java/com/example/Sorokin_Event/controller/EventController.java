package com.example.Sorokin_Event.controller;

import com.example.Sorokin_Event.dto.*;
import com.example.Sorokin_Event.mapper.EventMapper;
import com.example.Sorokin_Event.service.EventService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/events")
public class EventController {

    private final EventMapper mapper;
    private final EventService service;

    public EventController(EventMapper mapper, EventService service) {
        this.mapper = mapper;
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<EventDto> createEvents(@Valid @RequestBody EventResponseDto dto)
    {
        var model = mapper.toModel(dto);
        return new ResponseEntity<>(mapper.toDto(service.createEvent(model)), HttpStatus.CREATED);
    }

    @DeleteMapping("/{eventsId}")
    public ResponseEntity<Void> deleteById(@PathVariable Long eventsId)
    {

        service.deleteEventsById(eventsId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{eventsId}")
    public ResponseEntity<EventDto> getEventById(@PathVariable Long eventsId)
    {
        return new ResponseEntity<>(mapper.toDto(service.findEventById(eventsId)), HttpStatus.OK);
    }

   @PutMapping("/{eventsId}")
    public ResponseEntity<EventResponseDto> updateEventById(@PathVariable Long eventsId, @Valid @RequestBody EventUpdateDto dto)
    {
        var model = mapper.toModel(dto);
        return new ResponseEntity<>(mapper.toRespDto(service.updateEvents(eventsId, model)), HttpStatus.OK);
    }

    @PostMapping("/search")
    public ResponseEntity<List<EventDto>> searchEvents(@RequestBody EventSearchRequestDto dto)
    {
        var eventsList = service.searchEvents(dto);
        return new ResponseEntity<>(eventsList.stream().map(mapper::toDto).toList(), HttpStatus.OK);
    }


    @GetMapping("/my")
    public ResponseEntity<List<EventDto>> findAllUserEvents(){
        var eventsList = service.findAllUserEvents();
        return new ResponseEntity<>(eventsList.stream().map(mapper::toDto).toList(), HttpStatus.OK);
    }
}