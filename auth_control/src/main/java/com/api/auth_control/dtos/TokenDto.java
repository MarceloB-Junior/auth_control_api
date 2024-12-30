package com.api.auth_control.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record TokenDto(@NotBlank String token, @NotBlank String refreshToken) {
}
