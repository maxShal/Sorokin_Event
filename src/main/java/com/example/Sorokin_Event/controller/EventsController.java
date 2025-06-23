package com.example.Sorokin_Event.controller;

import com.example.Sorokin_Event.dto.*;
import com.example.Sorokin_Event.mapper.EventsMapper;
import com.example.Sorokin_Event.model.Events;
import com.example.Sorokin_Event.service.EventsService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/events")
public class EventsController {

    private final EventsMapper mapper;
    private final EventsService service;

    public EventsController(EventsMapper mapper, EventsService service) {
        this.mapper = mapper;
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<EventsDto> createEvents(@Valid @RequestBody EventsResponseDto dto)
    {
        var entity = mapper.toModel(dto);
        return new ResponseEntity<>(mapper.toDto(service.createEvent(entity)), HttpStatus.CREATED);
    }

    @DeleteMapping("/{eventsId}")
    public ResponseEntity<Void> deleteById(@PathVariable Long eventsId)
    {

        service.deleteEventsById(eventsId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{eventsId}")
    public ResponseEntity<EventsDto> getEventById(@PathVariable Long eventsId)
    {
        return new ResponseEntity<>(mapper.toDto(service.findEventById(eventsId)), HttpStatus.OK);
    }

   @PutMapping("/{eventsId}")
    public ResponseEntity<EventsResponseDto> updateEventById(@PathVariable Long eventsId, @Valid @RequestBody EventUpdateDto dto)
    {
        var entity = mapper.toModel(dto);
        return new ResponseEntity<>(mapper.toRespDto(service.updateEvents(eventsId, entity)), HttpStatus.OK);
    }

    @PostMapping("/search")
    public ResponseEntity<List<EventsDto>> searchEvents(@RequestBody EventSearchRequestDto dto)
    {
        var eventsList = service.searchEvents(dto);
        return new ResponseEntity<>(eventsList.stream().map(mapper::toDto).toList(), HttpStatus.OK);
    }


    @GetMapping("/my")
    public ResponseEntity<List<EventsDto>> findAllUserEvents(){
        var eventsList = service.findAllUserEvents();
        return new ResponseEntity<>(eventsList.stream().map(mapper::toDto).toList(), HttpStatus.OK);
    }
}