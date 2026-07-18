package com.hexticket.dto;

import lombok.Builder;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
@Builder
public class ErrorResponse {
    private String message;
    private String errorCode;
    private LocalDateTime timestamp;
}