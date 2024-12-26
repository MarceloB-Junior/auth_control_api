package com.api.auth_control.controllers;

import com.api.auth_control.dtos.UserDto;
import com.api.auth_control.exceptions.EmailAlreadyInUseException;
import com.api.auth_control.models.UserModel;
import com.api.auth_control.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/users")
public class UserController {

    final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserDto> saveUser(@RequestBody @Valid UserDto userDto){
        if(userService.existsByEmail(userDto.email())){
            throw new EmailAlreadyInUseException("Conflict: this email is already in use!");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(userDto));
    }

    @GetMapping
    public ResponseEntity<Page<UserModel>> getAllUsers(@PageableDefault(sort = "userId", direction = Sort.Direction.ASC)
                                                       Pageable pageable){
        return ResponseEntity.ok(userService.findAll(pageable));
    }

    @GetMapping("/admin-role")
    public ResponseEntity<String> getAdminMessage(){
        return ResponseEntity.ok("Welcome, you have admin permission.");
    }

    @GetMapping("/user-role")
    public ResponseEntity<String> getUserMessage(){
        return ResponseEntity.ok("Welcome, you have either admin or user permissions.");
    }
}
