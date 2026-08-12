package com.example.mini_jira.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.mini_jira.dto.TicketRequestDTO;
import com.example.mini_jira.dto.TicketResponseDTO;
import com.example.mini_jira.entity.ProjectEntity;
import com.example.mini_jira.entity.TicketEntity;
import com.example.mini_jira.entity.UserEntity;
import com.example.mini_jira.exception.InvalidStatusException;
import com.example.mini_jira.exception.ResourceNotFoundException;
import com.example.mini_jira.repository.ProjectRepository;
import com.example.mini_jira.repository.TicketRepository;
import com.example.mini_jira.repository.UserRepository;


@Service
public class TicketService {

    private static final Logger log = LoggerFactory.getLogger(TicketService.class);

    @Value("${ticket.statuses}")
    private List<String> validStatuses;

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

        log.info("Request recieved for Ticket.Creation title:{}", dto.title());
        
        ProjectEntity projectEntity = projectRepository.findById(dto.projectId())
            .orElseThrow(() -> new ResourceNotFoundException("projectId", "Project Not Found"));

        log.info("Project Found: {}", projectEntity.getName());
            
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        
        UserEntity reporterEntity = userRepository.findByUsername(username)
            .orElseThrow(() -> new ResourceNotFoundException("reporterId", "Reporter Not Found"));

        log.info("Reporter User Found: {}", reporterEntity.getUsername());

        TicketEntity ticketEntity = new TicketEntity();
        
        ticketEntity.setTitle(dto.title());
        ticketEntity.setDescription(dto.description());
        ticketEntity.setstatus("OPEN");
        ticketEntity.setProject(projectEntity);
        ticketEntity.setReporter(reporterEntity);

        ticketRepository.save(ticketEntity);

        log.info("Ticket Title:{} is inserted into database Status: {}", ticketEntity.getTitle(), ticketEntity.getStatus());

        return "Ticket is saved successfully";

    }

    @Transactional
    public String assignTicket(Long ticketId, String devUserName){

        log.info("Request recieved for Ticket:AssignDev for Ticket Id: {}", ticketId);

        TicketEntity ticketEntity = ticketRepository.findById(ticketId)
            .orElseThrow(() -> new ResourceNotFoundException("ticketId", "Ticket Not Found"));

        log.info("Ticket Title: {} Found", ticketEntity.getTitle());

        UserEntity developer = userRepository.findByUsername(devUserName)
            .orElseThrow(() -> new ResourceNotFoundException("devUserName", "Developer Not Found"));
        
        log.info("User Username: {} Found", developer.getUsername());

        String devRole = developer.getRole();

        if(!(devRole.equals("DEV") || devRole.equals("ADMIN"))){
            log.warn("User.Role is Not Admin or Developer throwing Error");
            throw new ResourceNotFoundException("devUserName", "Only developer and admins can be assigned");
        }

        ticketEntity.setAssignee(developer);
        ticketRepository.save(ticketEntity);

        log.info("Ticket Title: {} has assigned to User: {}", ticketEntity.getTitle(), developer.getUsername());

        return "Developer Assigned Sucessfully";
        
    }

    @Transactional
    public String updateTicketStatus(Long ticketId, String newStatus){

        log.info("Request to Update Ticket.Status : {}", newStatus);

        TicketEntity ticketEntity = ticketRepository.findById(ticketId)
            .orElseThrow(() -> new ResourceNotFoundException("tickedId", "Ticket Not Found"));

        log.info("Ticket Title: {} Found", ticketEntity.getTitle());
        
        if(!validStatuses.contains(newStatus.toUpperCase())){
            log.warn("Status {} Is Invalid", newStatus);
            throw new InvalidStatusException("Status", "Status is Invalid");
        }

        ticketEntity.setstatus(newStatus);
        ticketRepository.save(ticketEntity);

        log.info("Ticket Title: {} Status Updated To: {}", ticketEntity.getTitle(), ticketEntity.getStatus());

        return "Status Updated";
    }

    @Transactional(readOnly = true)
    public Slice<TicketResponseDTO> fetchTicketsByProject(Long projectId, int pageNumber, int pageSize){

        if(!projectRepository.existsById(projectId)){
            throw new ResourceNotFoundException("projectId", "Project Not Found");
        }
        
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        Slice<TicketEntity> ticketPage = ticketRepository.findByProjectId(projectId, pageable);

        return ticketPage
            .map(TicketResponseDTO::fromEntity);
    }

    @Transactional(readOnly = true)
    public Slice<TicketResponseDTO> fetchTicketsByAssignee(String assigneeUserName, int pageNumber, int pageSize){

        UserEntity assigneeUser = userRepository.findByUsername(assigneeUserName)
            .orElseThrow(() -> new ResourceNotFoundException("assigneeUserName", "Assignee Not Found"));

        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        Slice<TicketEntity> ticketPage = ticketRepository.findByAssigneeUsername(assigneeUser.getUsername(), pageable);

        return ticketPage
            .map(TicketResponseDTO::fromEntity);
    }

    @Transactional(readOnly = true)
    public Slice<TicketResponseDTO> fetchAssignedTickets(int pageNumber, int pageSize){

        String usernameCurrent = SecurityContextHolder.getContext().getAuthentication().getName();

        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        Slice<TicketEntity> ticketPage = ticketRepository.findByAssigneeUsername(usernameCurrent, pageable);

        return ticketPage
            .map(TicketResponseDTO::fromEntity);
    }

    @Transactional(readOnly = true)
    public Slice<TicketResponseDTO> fetchTicketsByReporter(String reporterUserName, int pageNumber, int pageSize){

        UserEntity reporterUser = userRepository.findByUsername(reporterUserName)
            .orElseThrow(() -> new ResourceNotFoundException("reporterUserName", "Reporter Not Found"));

        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        Slice<TicketEntity> ticketPage = ticketRepository.findByReporterUsername(reporterUser.getUsername(), pageable);

        return ticketPage
            .map(TicketResponseDTO::fromEntity);
    }

    @Transactional(readOnly = true)
    public Slice<TicketResponseDTO> fetchReportedTickets(int pageNumber, int pageSize){

        String usernameCurrent = SecurityContextHolder.getContext().getAuthentication().getName();
        
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        Slice<TicketEntity> ticketPage = ticketRepository.findByReporterUsername(usernameCurrent, pageable);

        return ticketPage
            .map(TicketResponseDTO::fromEntity);
    }
}
