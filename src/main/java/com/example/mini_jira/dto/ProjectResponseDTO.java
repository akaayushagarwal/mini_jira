package com.example.mini_jira.dto;

import com.example.mini_jira.entity.ProjectEntity;

public record ProjectResponseDTO(
    
    Long id,
    
    String name,
    
    String description

) {
    public static ProjectResponseDTO fromEntity(ProjectEntity entity){
        return new ProjectResponseDTO(
            entity.getId(),
            entity.getName(),
            entity.getDescription()
        );
    }
}
