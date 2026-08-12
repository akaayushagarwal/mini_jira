package com.example.mini_jira.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.mini_jira.dto.CommentRequestDTO;
import com.example.mini_jira.dto.CommentResponseDTO;
import com.example.mini_jira.entity.CommentEntity;
import com.example.mini_jira.entity.TicketEntity;
import com.example.mini_jira.entity.UserEntity;
import com.example.mini_jira.exception.ResourceNotFoundException;
import com.example.mini_jira.repository.CommentRepository;
import com.example.mini_jira.repository.TicketRepository;
import com.example.mini_jira.repository.UserRepository;


@Service
public class CommentService {

    private static final Logger log = LoggerFactory.getLogger(CommentService.class);

    private final CommentRepository commentRepository;
    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;

    public CommentService(CommentRepository commentRepository, TicketRepository ticketRepository, UserRepository userRepository){
        this.commentRepository = commentRepository;
        this.ticketRepository = ticketRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public String createComment(CommentRequestDTO dto){

        log.info("Request recieved for Comment.Creation for Ticket Id: {}", dto.ticketId());

        TicketEntity ticket = ticketRepository.findById(dto.ticketId())
            .orElseThrow(() -> new ResourceNotFoundException("ticketId", "Ticket Not Found"));

        log.info("Ticket Found: {}", ticket.getTitle());

        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        UserEntity author = userRepository.findByUsername(username)
            .orElseThrow(() -> new ResourceNotFoundException("authorId", "Author Not Found"));
        
        log.info("Author User Found: {}", author.getUsername());

        CommentEntity entity = new CommentEntity();
        entity.setText(dto.text());
        entity.setTicket(ticket);
        entity.setAuthor(author);

        commentRepository.save(entity);

        log.info("Comment Id: {} with Ticket Title: {} is inserted into database", entity.getId(), ticket.getTitle());

        return "Comment is saved successfully";

    }

    @Transactional(readOnly = true)
    public List<CommentResponseDTO> fetchCommentsByTicket(Long ticketId){

        log.info("Request recieved for find.Comments associated with Ticket Id: {}", ticketId);

        TicketEntity ticket = ticketRepository.findById(ticketId)
            .orElseThrow(() -> new ResourceNotFoundException("ticketId", "Ticket Not Found"));

        log.info("Ticket Found: {}", ticket.getTitle());

        List<CommentEntity> allTicketComments = commentRepository.findByTicketId(ticket.getId());

        log.info("{} Comments found associated with this ticket", allTicketComments.size());
        
        return allTicketComments
            .stream()
            .map(CommentResponseDTO::fromEntity)
            .toList();
    }
}
