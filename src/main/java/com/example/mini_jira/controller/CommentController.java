package com.example.mini_jira.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.mini_jira.dto.CommentRequestDTO;
import com.example.mini_jira.dto.CommentResponseDTO;
import com.example.mini_jira.service.CommentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService){
        this.commentService = commentService;
    }

    @PostMapping
    public ResponseEntity<String> addComment(@Valid @RequestBody CommentRequestDTO dto){

        String response = commentService.createComment(dto);

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(response);
    }

    @GetMapping("/ticket/{ticketId}")
    public ResponseEntity<List<CommentResponseDTO>> getCommentsByTicket(@PathVariable Long ticketId){

        List<CommentResponseDTO> allComments = commentService.fetchCommentsByTicket(ticketId);

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(allComments);
    }

}
