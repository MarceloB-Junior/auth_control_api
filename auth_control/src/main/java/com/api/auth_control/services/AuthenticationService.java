package com.api.auth_control.services;

import com.api.auth_control.dtos.LoginDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AuthenticationService extends UserDetailsService {
    String obtainJwtToken(LoginDto loginDto);
    String validateJwtToken(String jwtToken);
}
