package com.example.Sorokin_Event.controller;

import com.example.Sorokin_Event.dto.UserDto;
import com.example.Sorokin_Event.mapper.UserMapper;
import com.example.Sorokin_Event.security.SignUpRequest;
import com.example.Sorokin_Event.service.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

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
    public ResponseEntity<UserDto> registerUser(@Valid @RequestBody SignUpRequest sign)
    {
        log.info("Логин нового пользователя: " + sign.getLogin());
        var user = service.registerUser(sign);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new UserDto(user.getId(), user.getLogin()));
    }

//6:46

}
