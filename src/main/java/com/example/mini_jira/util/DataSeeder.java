package com.example.mini_jira.util;

import org.springframework.stereotype.Component;

import com.example.mini_jira.entity.ProjectEntity;
import com.example.mini_jira.entity.UserEntity;
import com.example.mini_jira.repository.ProjectRepository;
import com.example.mini_jira.repository.UserRepository;

import jakarta.annotation.PostConstruct;

@Component
public class DataSeeder {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public DataSeeder(ProjectRepository projectRepository, UserRepository userRepository){
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    @PostConstruct
    public void insertTestData(){
        if(projectRepository.count() == 0){
            ProjectEntity testProject = new ProjectEntity();
            testProject.setName("Product Listing Page");
            testProject.setDescription("The page where all the product is listed");
            projectRepository.save(testProject);
        }
        if(userRepository.count() == 0){
            UserEntity testUser = new UserEntity();
            testUser.setUsername("Kyle");
            testUser.setRole("QA");
            testUser.setPassword("QA@123");
            userRepository.save(testUser);
        }
    }

}
