package com.api.auth_control.controllers;

import com.api.auth_control.dtos.LoginDto;
import com.api.auth_control.dtos.RefreshTokenDto;
import com.api.auth_control.dtos.TokenDto;
import com.api.auth_control.services.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/auth")
public class AuthenticationController {

    final AuthenticationManager authenticationManager;
    final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<TokenDto> authUser(@RequestBody @Valid LoginDto loginDto) {
        var userAuthToken = new UsernamePasswordAuthenticationToken(loginDto.email(), loginDto.password());
        authenticationManager.authenticate(userAuthToken);
        return ResponseEntity.ok(authenticationService.obtainJwtToken(loginDto));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<TokenDto> authRefreshToken(@RequestBody @Valid RefreshTokenDto refreshTokenDto){
        return ResponseEntity.ok(authenticationService
                .obtainJwtRefreshToken(refreshTokenDto.refreshToken()));
    }
}