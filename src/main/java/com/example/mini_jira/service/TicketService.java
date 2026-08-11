package com.example.mini_jira.service;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.mini_jira.dto.TicketRequestDTO;
import com.example.mini_jira.entity.ProjectEntity;
import com.example.mini_jira.entity.TicketEntity;
import com.example.mini_jira.entity.UserEntity;
import com.example.mini_jira.exception.ResourceNotFoundException;
import com.example.mini_jira.repository.ProjectRepository;
import com.example.mini_jira.repository.TicketRepository;
import com.example.mini_jira.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public TicketService(TicketRepository ticketRepository, ProjectRepository projectRepository, UserRepository userRepository){
        this.ticketRepository = ticketRepository;
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public String createTicket(TicketRequestDTO dto){
        
        ProjectEntity projectEntity = projectRepository.findById(dto.projectId())
            .orElseThrow(() -> new ResourceNotFoundException("projectId", "Project Not Found"));

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        
        UserEntity reporterEntity = userRepository.findByUsername(username)
            .orElseThrow(() -> new ResourceNotFoundException("reporterId", "Reporter Not Found"));

        TicketEntity ticketEntity = new TicketEntity();
        
        ticketEntity.setTitle(dto.title());
        ticketEntity.setDescription(dto.description());
        ticketEntity.setstatus("OPEN");
        ticketEntity.setProject(projectEntity);
        ticketEntity.setReporter(reporterEntity);

        ticketRepository.save(ticketEntity);

        return "Ticket is saved successfully";

    }
}
