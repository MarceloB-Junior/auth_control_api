package com.api.auth_control.dtos;

import jakarta.validation.constraints.NotBlank;

public record TokenDto(
        @NotBlank
        String token,
        @NotBlank
        String message
) {
}
