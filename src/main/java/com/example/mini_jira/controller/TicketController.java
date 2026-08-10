package com.example.mini_jira.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.mini_jira.dto.TicketRequestDTO;
import com.example.mini_jira.service.TicketService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/tickets")
public class TicketController {

    private final TicketService ticketService;

    public TicketController(TicketService ticketService){
        this.ticketService = ticketService;
    }

    @PostMapping
    public ResponseEntity<String> addTicket(@Valid @RequestBody TicketRequestDTO dto) {
        
        String response = ticketService.createTicket(dto);

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(response);
    }
    
}
