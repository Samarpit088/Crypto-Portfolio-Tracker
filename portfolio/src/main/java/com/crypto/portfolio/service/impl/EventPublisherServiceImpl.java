package com.crypto.portfolio.service.impl;

import com.crypto.portfolio.service.EventPublisherService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventPublisherServiceImpl implements EventPublisherService {
    private final RabbitTemplate rabbitTemplate;

    @Override
    public void publishUserRegisteredEvents(String email){
        rabbitTemplate.convertAndSend("user.registered.queue","User registered: " + email);
    }

    @Override
    public void publishAlertTriggeredEvents(String symbol,String email){
        rabbitTemplate.convertAndSend("alert.triggered.queue","Alert triggered for: " + symbol +" | Email: " + email);
    }
}
