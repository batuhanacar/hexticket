package com.hexticket.exception;

import com.hexticket.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(SeatAlreadySoldException.class)
    public ResponseEntity<ErrorResponse> handleSeatAlreadySold(SeatAlreadySoldException ex) {
        return buildResponse(ex.getMessage(), "SEAT_ALREADY_SOLD", HttpStatus.CONFLICT);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException ex) {
        return buildResponse(ex.getMessage(), "INTERNAL_SERVER_ERROR", HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<ErrorResponse> buildResponse(String message, String code, HttpStatus status) {
        ErrorResponse response = ErrorResponse.builder()
                .message(message)
                .errorCode(code)
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(response, status);
    }
}