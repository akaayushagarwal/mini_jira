package com.example.mini_jira.dto;

import com.example.mini_jira.entity.UserEntity;

public record UserResponseDTO(

    Long id,
    String username,
    String role,
    String email

) {

    public static UserResponseDTO fromEntity(UserEntity entity){
        return new UserResponseDTO(
            entity.getId(),
            entity.getUsername(),
            entity.getRole(),
            entity.getEmail()
        );
    }

}
