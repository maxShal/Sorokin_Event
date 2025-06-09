package com.example.Sorokin_Event.controller;

import com.example.Sorokin_Event.dto.UserDto;
import com.example.Sorokin_Event.mapper.UserMapper;
import com.example.Sorokin_Event.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserMapper mapper;
    private UserService service;

    public UserController(UserMapper mapper, UserService service) {
        this.mapper = mapper;
        this.service = service;
    }

    @GetMapping("/userId")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id){
        return new ResponseEntity<>(mapper.toDto(service.findById(id)), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto dto)
    {
        return new ResponseEntity<>(mapper.toDto(service.createUser(mapper.toModel(dto))),HttpStatus.CREATED);
    }



}
