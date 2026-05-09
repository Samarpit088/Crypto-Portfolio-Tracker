package com.crypto.portfolio.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabitMQConfig {

    @Bean
    public Queue userRegisterQueue(){
        return new Queue("user.registered.queue",true);
    }

    @Bean
    public Queue alertTriggeredQueue(){
        return new Queue("alert.triggered.queue",true);
    }
}
