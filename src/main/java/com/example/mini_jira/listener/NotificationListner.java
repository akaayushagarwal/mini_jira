package com.example.mini_jira.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.example.mini_jira.config.RabbitMQConfig;
import com.example.mini_jira.dto.TicketNotificationEvent;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;

@Component
public class NotificationListner {

    public static final Logger log = LoggerFactory.getLogger(NotificationListner.class);

    @Value("${sendgrid.api-key}")
    private String sendGridApiKey;

    @Value("${sendgrid.from-email}")
    private String fromEmailAddress;

    @RabbitListener(queues = RabbitMQConfig.NOTIFICATION_QUEUE)
    public void handleTicketNotification(TicketNotificationEvent event) {
        log.info("📩 ASYNC NOTIFICATION RECEIVED: Sending email for Ticket ID {}", event.ticketId());

        Email from = new Email(fromEmailAddress);
        String subject = "URGENT: Mini-Jira Ticket Update - " + event.ticketName();
        Email to = new Email(event.email());

        String emailBody = String.format("""
            A new ticket event requires your attention.
                
            Ticket ID: %d
            Ticket Name: %s
            Current Status: %s
                
            System Message: %s
            """, 
            event.ticketId(),
            event.ticketName(),
            event.status(),
            event.message());

        Content content = new Content("text/plain", emailBody);
        Mail mail = new Mail(from, subject, to, content);

        SendGrid sg = new SendGrid(sendGridApiKey);
        Request request = new Request();

        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());

            Response response = sg.api(request);
            if (response.getStatusCode() == 202 || response.getStatusCode() == 200) {
                log.info("✅ Email successfully sent via SendGrid API for Ticket ID: {}", event.ticketId());
            } else {
                log.error("⚠️ SendGrid API failed with status code: {}", response.getStatusCode());
                log.error("Response body: {}", response.getBody());
            }
            
        } catch (Exception e) {
            log.error("❌ Failed to send email for Ticket ID: {}", event.ticketId(), e);
        }
    }

}
