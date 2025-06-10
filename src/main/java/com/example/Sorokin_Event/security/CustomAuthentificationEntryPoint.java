package com.example.Sorokin_Event.security;

import com.example.Sorokin_Event.exception.ErrorMessageResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
public class CustomAuthentificationEntryPoint implements AuthenticationEntryPoint {

    private static final Logger log = LoggerFactory.getLogger(CustomAuthentificationEntryPoint.class);

    private final ObjectMapper objectMapper;

    public CustomAuthentificationEntryPoint(ObjectMapper objectMapper) {

        this.objectMapper = objectMapper;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        log.error("Ошибка аутентификации", authException);
        var messageResponse = new ErrorMessageResponse(
                "Ошибка аутентификации",
                authException.getMessage(),
                LocalDateTime.now()
        );
        var stringResponse = objectMapper.writeValueAsString(messageResponse);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);//узнать!
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.getWriter().write(stringResponse);
    }
}
