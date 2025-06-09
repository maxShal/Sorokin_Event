package com.example.Sorokin_Event.controller;

import com.example.Sorokin_Event.dto.LocationDto;
import com.example.Sorokin_Event.dto.LocationResponseDto;
import com.example.Sorokin_Event.mapper.LocationMapper;
import com.example.Sorokin_Event.model.Location;
import com.example.Sorokin_Event.service.LocationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/locations")
public class LocationController {


    private final LocationMapper mapper;

    private LocationService service;


    public LocationController(LocationMapper mapper, LocationService service)
    {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping("/{locationId}")
    public ResponseEntity<LocationDto> getLocationById(@PathVariable Long id)
    {
        return new ResponseEntity<>(mapper.toDto(service.findById(id)), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<LocationDto>> getLocations()
    {
        List<Location> locations = service.findAll();
        List<LocationDto> dtos = locations.stream()
                .map(mapper::toDto).toList();
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<LocationResponseDto> createLocation(@Valid @RequestBody LocationDto dto)
    {
        return new ResponseEntity<>(mapper.toDtoResp(service.createLocation(mapper.toModel(dto))), HttpStatus.CREATED);
    }

    @PutMapping("/{locationId}")
    public ResponseEntity<LocationResponseDto> updateLocation(@PathVariable Long id, @Valid @RequestBody LocationDto dto)
    {
        Location location = mapper.toModel(dto);
        location.setId(id);
        return new ResponseEntity<>(mapper.toDtoResp(service.updateLocation(location)), HttpStatus.OK);
    }

    @DeleteMapping("/{locationId}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id)
    {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
