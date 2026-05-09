package com.crypto.portfolio.service.impl;

import com.crypto.portfolio.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Override
    public void sendWelcomeEmail(String toEmail,String name){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(toEmail);
        message.setSubject("Welcome to Crypto Portfolio Tracker");
        message.setText("Hi 👋🏻" + name + ",\n\nWelcome to Crypto Portfolio Tracker!📈");

        javaMailSender.send(message);
    }

    @Override
    public void sendAlertTriggeredEmail(String toEmail,String symbol,String triggerPrice){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(toEmail);
        message.setSubject("Crypto Alert Triggered");
        message.setText("Your alert for" + symbol + " has been triggered at price: " + triggerPrice);

        javaMailSender.send(message);
    }
}
