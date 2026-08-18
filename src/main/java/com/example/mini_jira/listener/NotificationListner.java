package com.example.mini_jira.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.example.mini_jira.config.RabbitMQConfig;
import com.example.mini_jira.dto.TicketNotificationEvent;

@Component
public class NotificationListner {

    public static final Logger log = LoggerFactory.getLogger(NotificationListner.class);

    private final JavaMailSender javaMailSender;

    public NotificationListner(JavaMailSender javaMailSender){
        this.javaMailSender = javaMailSender;
    }

    @RabbitListener(queues = RabbitMQConfig.NOTIFICATION_QUEUE)
    public void handleTicketNotification(TicketNotificationEvent event) {
        log.info("📩 ASYNC NOTIFICATION RECEIVED: Sending email for Ticket ID {}", event.ticketId());

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("rabbitmq.minijira@gmail.com"); 
            message.setTo(event.email());   
            message.setSubject("URGENT: Mini-Jira Ticket Update - " + event.ticketName());
            
            String emailBody = String.format("""
                A new ticket event requires your attention.
                
                Ticket ID: %d
                Ticket Name: %s
                Current Status: %s
                
                System Message: %s
                """, 
                event.ticketId(), event.ticketName(), event.status(), event.message());

            message.setText(emailBody);
            
            javaMailSender.send(message);
            log.info("✅ Email successfully sent for Ticket ID: {}", event.ticketId());
            
        } catch (Exception e) {
            log.error("❌ Failed to send email for Ticket ID: {}", event.ticketId(), e);
        }
    }

}
