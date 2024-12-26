package com.api.auth_control.services.impl;

import com.api.auth_control.dtos.LoginDto;
import com.api.auth_control.models.UserModel;
import com.api.auth_control.repositories.UserRepository;
import com.api.auth_control.services.AuthenticationService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    final UserRepository userRepository;

    @Value("${auth.jwt.token.secret}")
    private String jwtSecret;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
         return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
    }

    @Override
    public String obtainJwtToken(@NotNull LoginDto loginDto) throws UsernameNotFoundException{
        UserModel userModel = userRepository.findByEmail(loginDto.email())
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + loginDto.email()));
        return generateJwtToken(userModel);
    }

    public String generateJwtToken(@NotNull UserModel userModel){
       try {
           Algorithm algorithm = Algorithm.HMAC256(jwtSecret);

           return JWT.create()
                   .withIssuer("auth_control")
                   .withSubject(userModel.getEmail())
                   .withExpiresAt(generateExpirationDate())
                   .sign(algorithm);

       } catch (JWTCreationException ex){
           throw new RuntimeException("error in generating the JWT token: " + ex.getMessage());
       }
    }

    private Instant generateExpirationDate() {
        return LocalDateTime.now().plusHours(8)
                .toInstant(ZoneOffset.of("-03:00"));
    }

    @Override
    public String validateJwtToken(String jwtToken){
        try {
            Algorithm algorithm = Algorithm.HMAC256(jwtSecret);

            return JWT.require(algorithm)
                    .withIssuer("auth_control")
                    .build()
                    .verify(jwtToken)
                    .getSubject();

        } catch (JWTVerificationException ex){
            throw new RuntimeException("Invalid JWT token: " + ex.getMessage());
        }
    }
}
