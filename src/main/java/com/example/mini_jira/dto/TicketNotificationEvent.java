package com.example.mini_jira.dto;

import com.example.mini_jira.entity.TicketEntity;

public record TicketNotificationEvent(

    Long ticketId,
    String ticketName,
    String status,
    String message
) {
    public static TicketNotificationEvent fromEntity(TicketEntity entity){
        return new TicketNotificationEvent(
            entity.getId(),
            entity.getTitle(),
            entity.getStatus(),
            "A new critical bug ticket has been created and requires immediate attention."
        );
    }
}
