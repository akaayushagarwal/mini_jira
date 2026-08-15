package com.example.mini_jira.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.mini_jira.dto.ProjectRequestDTO;
import com.example.mini_jira.dto.ProjectResponseDTO;
import com.example.mini_jira.entity.ProjectEntity;
import com.example.mini_jira.repository.ProjectRepository;


@Service
public class ProjectService {

    private static final Logger log = LoggerFactory.getLogger(ProjectService.class);
    
    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository){
        this.projectRepository = projectRepository;
    }

    @Transactional
    @CacheEvict(value = "projectCache", allEntries = true)
    public String createProject(ProjectRequestDTO dto){

        log.info("Request recieved for Project.Creation name: {}", dto.name());

        ProjectEntity entity = new ProjectEntity();
        entity.setName(dto.name());
        entity.setDescription(dto.description());

        projectRepository.save(entity);

        log.info("Project Name: {} is inserted into database", entity.getName());

        return "Project is successfully created";
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "projectCache")
    public List<ProjectResponseDTO> fetchAllProjects(){

        log.info("Fetching All Projects...");
        
        return projectRepository.findAll()
            .stream()
            .map(ProjectResponseDTO::fromEntity)
            .toList();
            
    }
}
