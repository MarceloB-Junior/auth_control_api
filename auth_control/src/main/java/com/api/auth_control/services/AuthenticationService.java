package com.api.auth_control.services;

import com.api.auth_control.dtos.LoginDto;
import com.api.auth_control.dtos.TokenDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AuthenticationService extends UserDetailsService {
    TokenDto obtainJwtToken(LoginDto loginDto);
    TokenDto obtainJwtRefreshToken(String refreshToken);
    String validateJwtToken(String jwtToken);
}
