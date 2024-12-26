package com.api.auth_control.dtos;

import java.time.LocalDateTime;

public record ExceptionsDto(
        LocalDateTime timestamp,
        int status,
        String error,
        String message
) {
}