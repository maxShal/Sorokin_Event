package com.example.Sorokin_Event.security.jwt;

import com.example.Sorokin_Event.model.User;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.login(),
                        request.password()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return manager.generateToken(authentication);
    }

    public User getCurrentAuthentificatedUser()
    {
        var authantification = SecurityContextHolder.getContext().getAuthentication();
        if(authantification == null)
        {
            throw new IllegalStateException("Нет юзера");
        }
        return (User) authantification.getPrincipal();
    }
}
