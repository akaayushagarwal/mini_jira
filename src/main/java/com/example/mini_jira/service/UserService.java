package com.example.mini_jira.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.mini_jira.dto.UserRequestDTO;
import com.example.mini_jira.dto.UserResponseDTO;
import com.example.mini_jira.entity.UserEntity;
import com.example.mini_jira.exception.InvalidRoleException;
import com.example.mini_jira.exception.UserAlreadyPresentException;
import com.example.mini_jira.repository.UserRepository;

@Service
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Value("${user.roles}")
    private List<String> validRoles;

    public UserService(PasswordEncoder passwordEncoder, UserRepository userRepository){
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @Transactional
    public String registerUser(UserRequestDTO dto){

        if(userRepository.existsByUsername(dto.username())){
            throw new UserAlreadyPresentException();
        }

        if(!validRoles.contains(dto.role())){
            throw new InvalidRoleException();
        }

        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(dto.username());
        userEntity.setPassword(passwordEncoder.encode(dto.password()));
        userEntity.setRole(dto.role().toUpperCase());
        
        userRepository.save(userEntity);

        return "User Registered";
        
    }

    @Transactional(readOnly = true)
    public List<UserResponseDTO> fetchAllUsers(){

        return userRepository.findAll()
            .stream()
            .map(UserResponseDTO::fromEntity)
            .toList();
    }
}
