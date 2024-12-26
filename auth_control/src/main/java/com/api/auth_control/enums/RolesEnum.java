package com.api.auth_control.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RolesEnum {
    ADMIN("admin"),
    USER("user");

    final String role;
}
