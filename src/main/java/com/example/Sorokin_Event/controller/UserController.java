package com.example.Sorokin_Event.controller;

import com.example.Sorokin_Event.dto.UserDto;
import com.example.Sorokin_Event.mapper.UserMapper;
import com.example.Sorokin_Event.security.SignUpRequest;
import com.example.Sorokin_Event.security.jwt.JwtAuthentificationService;
import com.example.Sorokin_Event.security.jwt.JwtTokenResponse;
import com.example.Sorokin_Event.security.jwt.SignInRequest;
import com.example.Sorokin_Event.service.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    private final UserMapper mapper;
    private final UserService service;
    private final JwtAuthentificationService authentificationService;

    public UserController(UserMapper mapper, UserService service, JwtAuthentificationService authentificationService) {
        this.mapper = mapper;
        this.service = service;
        this.authentificationService = authentificationService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long userId){
        return new ResponseEntity<>(mapper.toDto(service.findById(userId)), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UserDto> registerUser(@Valid @RequestBody SignUpRequest sign)
    {
        log.info("Логин нового пользователя: {}", sign.getLogin());
        var user = service.registerUser(sign);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new UserDto(user.getId(), user.getLogin(), user.getRole(),user.getPasswordHash()));
    }

    @PostMapping("/auth")
    public ResponseEntity<JwtTokenResponse> authenticate(@Valid @RequestBody SignInRequest request)
    {
        log.info("Логин пользователя: {} ", request.login());
        var token = authentificationService.authenticateUser(request);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new JwtTokenResponse(token));
    }

}
