package com.example.mini_jira.repository;

import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.mini_jira.entity.TicketEntity;

@Repository
public interface TicketRepository extends JpaRepository<TicketEntity, Long>{

    Slice<TicketEntity> findByProjectId(Long projectId, Pageable pageable);

}
