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
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
public class CustomAccessDenied implements AccessDeniedHandler {

    private static final Logger log = LoggerFactory.getLogger(CustomAccessDenied.class);

    private final ObjectMapper objectMapper;

    public CustomAccessDenied(ObjectMapper objectMapper) {

        this.objectMapper = objectMapper;
    }
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        log.error("Ошибка доступа", accessDeniedException);
        var messageResponse = new ErrorMessageResponse(
                "Недостаточно прав для выполнения операции",
                accessDeniedException.getMessage(),
                LocalDateTime.now()
        );
        var stringResponse = objectMapper.writeValueAsString(messageResponse);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);//узнать!
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.getWriter().write(stringResponse);
    }
}
