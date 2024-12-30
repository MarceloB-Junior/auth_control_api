package com.api.auth_control.services;

import com.api.auth_control.dtos.RegisterDto;
import com.api.auth_control.dtos.UserDto;
import com.api.auth_control.enums.RolesEnum;
import com.api.auth_control.models.UserModel;
import com.api.auth_control.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    final UserRepository userRepository;
    final PasswordEncoder passwordEncoder;

    @Transactional
    public UserDto save(RegisterDto registerDto) {
        var userModel = new UserModel();
        BeanUtils.copyProperties(registerDto,userModel);

        userModel.setPassword(passwordEncoder.encode(userModel.getPassword()));
        userModel.setRole(RolesEnum.USER);
        userRepository.save(userModel);

        return new UserDto(userModel.getName(),userModel.getEmail());
    }

    public boolean existsByEmail(String email){
        return userRepository.existsByEmail(email);
    }

    public Page<UserModel> findAll(Pageable pageable){
        return userRepository.findAll(pageable);
    }
}

