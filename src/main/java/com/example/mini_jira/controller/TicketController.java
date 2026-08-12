package com.example.mini_jira.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.mini_jira.dto.TicketRequestDTO;
import com.example.mini_jira.dto.TicketResponseDTO;
import com.example.mini_jira.service.TicketService;

import jakarta.validation.Valid;

import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

    @PutMapping("/{id}/assign/{devUserName}")
    public ResponseEntity<String> assignDev(@PathVariable Long id, @PathVariable String devUserName){

        String response = ticketService.assignTicket(id, devUserName);

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(response);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<String> updateStatus(@PathVariable Long id, @RequestParam String status){

        String response = ticketService.updateTicketStatus(id, status);

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(response);
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<Slice<TicketResponseDTO>> getTicketsForProject(
        @PathVariable Long projectId,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size
    ){

        Slice<TicketResponseDTO> response = ticketService.fetchTicketsByProject(projectId, page, size);

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(response);
    }
    
}
