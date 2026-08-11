package com.example.mini_jira.controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.mini_jira.dto.ProjectRequestDTO;
import com.example.mini_jira.dto.ProjectResponseDTO;
import com.example.mini_jira.service.ProjectService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/projects")
public class ProjectController {
    
    private final ProjectService projectService;

    public ProjectController(ProjectService projectService){
        this.projectService = projectService;
    }

    @PostMapping
    public ResponseEntity<String> addProject(@Valid @RequestBody ProjectRequestDTO dto) {
        
        String response = projectService.createProject(dto);

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(response);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ProjectResponseDTO>> getAllProjects(){

        List<ProjectResponseDTO> allProjects = projectService.fetchAllProjects();

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(allProjects);
    }
    
}
