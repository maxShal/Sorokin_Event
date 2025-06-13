package com.example.Sorokin_Event.security.jwt;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class JwtAuthentificationService {

    private final AuthenticationManager authenticationManager;

    private final JwtTokenManager manager;

    public JwtAuthentificationService(AuthenticationManager authenticationManager, JwtTokenManager manager) {
        this.authenticationManager = authenticationManager;
        this.manager = manager;
    }

    public String authenticateUser(SignInRequest request ){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.login(),
                        request.password()
                )
        );
        return manager.generateToken(request.login());
    }
}
