package com.crypto.portfolio.service;

public interface EmailService {
    void sendWelcomeEmail(String toEmail,String name);
    void sendAlertTriggeredEmail(String toEmail,String symbol,String triggerPrice);
}
