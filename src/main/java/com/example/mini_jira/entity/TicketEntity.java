package com.example.mini_jira.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class TicketEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String title;

    @Column
    private String description;

    @Column
    private String status;

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private ProjectEntity project;

    @ManyToOne
    @JoinColumn(name = "reporter_id", nullable = false)
    private UserEntity reporter;

    @ManyToOne
    @JoinColumn(name = "assignee_id")
    private UserEntity assignee;

    @OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL)
    private List<CommentEntity> comments;

    public TicketEntity(){}

    public Long getId(){return id;}

    public String getTitle(){return title;}
    public void setTitle(String title){this.title = title;}

    public String getDescription(){return description;}
    public void setDescription(String description){this.description = description;}

    public String getStatus(){return status;}
    public void setstatus(String status){this.status = status;}

    public ProjectEntity getProject(){return project;}
    public void setProject(ProjectEntity project){this.project = project;}

    public UserEntity getReporter(){return reporter;}
    public void setReporter(UserEntity reporter){this.reporter = reporter;}

    public UserEntity getAssignee(){return assignee;}
    public void setAssignee(UserEntity assignee){this.assignee = assignee;}

}
