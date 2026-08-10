package com.example.mini_jira.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.mini_jira.entity.TicketEntity;

@Repository
public interface TicketRepository extends JpaRepository<TicketEntity, Long>{

}
