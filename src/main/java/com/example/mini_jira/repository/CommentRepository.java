package com.example.mini_jira.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.mini_jira.entity.CommentEntity;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long>{

    List<CommentEntity> findByTicketId(Long ticketId);
    
}
