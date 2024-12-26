package com.api.auth_control.configs.security;

import com.api.auth_control.models.UserModel;
import com.api.auth_control.repositories.UserRepository;
import com.api.auth_control.services.AuthenticationService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class SecurityFilter extends OncePerRequestFilter {

    final AuthenticationService authenticationService;
    final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String jwtToken = extractTokenHeader(request);

        if(jwtToken != null) {
            String login = authenticationService.validateJwtToken(jwtToken);

            Optional<UserModel> userModel = userRepository.findByEmail(login);

            if(userModel.isPresent()) {
                var authentication = new UsernamePasswordAuthenticationToken(userModel,
                        null, userModel.get().getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }else {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                        "Invalid JWT token or user not found");
                return;
            }
        }

        filterChain.doFilter(request,response);
    }

    public String extractTokenHeader(HttpServletRequest request){
        var authorizationHeader = request.getHeader("Authorization");

        if(authorizationHeader == null || !authorizationHeader.split(" ")[0].equals("Bearer")){
           return null;
        }
        return authorizationHeader.split(" ")[1];
    }
}
