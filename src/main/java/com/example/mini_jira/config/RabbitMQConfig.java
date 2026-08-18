package com.example.mini_jira.config;


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String TICKET_EXCHANGE = "ticket.exchange";
    public static final String NOTIFICATION_QUEUE = "ticket.notification.queue";
    public static final String ROUTING_KEY = "ticket.assigned.routing.key";

    @Bean
    public Queue notificationQueue(){
        return new Queue(NOTIFICATION_QUEUE, true);
    }

    @Bean
    public DirectExchange ticketExchange(){
        return new DirectExchange(TICKET_EXCHANGE);
    }

    @Bean
    public Binding binding(Queue notificationQueue, DirectExchange ticketExchange){
        return BindingBuilder.bind(notificationQueue)
            .to(ticketExchange)
            .with(ROUTING_KEY);
    }

    @Bean
    public MessageConverter jsonMessageConverter(){
        return new JacksonJsonMessageConverter();
    }
}
