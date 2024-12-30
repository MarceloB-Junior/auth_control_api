package com.api.auth_control.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.Instant;

@Builder
public record TokenDto(
        @NotBlank
        String accessToken,
        @NotBlank
        String refreshToken,
        @NotNull
        Instant expiresAt
) {
}
