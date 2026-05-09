package com.crypto.portfolio.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EventConsumerService {

    @RabbitListener(queues="user.registered.queue")
    public void consumeUserRegisteredEvent(String message){
        log.info("Received user registered event: {}",message);
    }

    @RabbitListener(queues="alert.triggered.queue")
    public void consumeAlertTriggeredEvent(String message){
        log.info("Received alert triggered event: {}",message);
    }
}
