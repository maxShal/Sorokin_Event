package com.example.Sorokin_Event.exception;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;


@RestControllerAdvice
public class GlobalExceptionHandler {
    ErrorMessageResponse response;

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    @ExceptionHandler(value =
            {
                    IllegalArgumentException.class,
                    MethodArgumentNotValidException.class})
    public ResponseEntity<Object> handleIllegalArgument(Exception ex)
    {
        log.error("Неверный запрос", ex);
        ErrorMessageResponse resp = new ErrorMessageResponse(
                "Неверный запрос",
                ex.getMessage(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(400).body(resp);
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<Object> handleAuthorizationException(AuthorizationDeniedException ex)
    {
        log.error("Неверный запрос", ex);
        ErrorMessageResponse resp = new ErrorMessageResponse(
                "Недостаточно прав для выполнения операции",
                ex.getMessage(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(resp);
    }

    @ExceptionHandler(value = {
            Exception.class})
    public ResponseEntity<Object> handleGenericException(Exception ex)
    {
        log.error("Непредвиденная ошибка",ex);
        ErrorMessageResponse resp  = new ErrorMessageResponse(
                "Внутренняя ошибка",
                ex.getMessage(),
                LocalDateTime.now()
        );
        return  ResponseEntity.status(418).body(resp);

    }
}
