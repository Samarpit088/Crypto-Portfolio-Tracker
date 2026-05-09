package com.crypto.portfolio.service;

public interface EventPublisherService {
    void publishUserRegisteredEvents(String email);
    void publishAlertTriggeredEvents(String symbol,String email);
}
