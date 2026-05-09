package com.crypto.portfolio.service.impl;

import com.crypto.portfolio.dto.AlertRequest;
import com.crypto.portfolio.dto.AlertResponse;
import com.crypto.portfolio.entity.Alert;
import com.crypto.portfolio.entity.User;
import com.crypto.portfolio.repository.AlertRepository;
import com.crypto.portfolio.repository.CryptoPriceRepository;
import com.crypto.portfolio.repository.UserRepository;
import com.crypto.portfolio.service.AlertService;
import com.crypto.portfolio.service.EmailService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AlertServiceImpl implements AlertService {
    private final AlertRepository alertRepository;
    private final UserRepository userRepository;
    private final CryptoPriceRepository cryptoPriceRepository;
    private final EmailService emailService;

    @Override
    public AlertResponse createAlert(AlertRequest request,String email){
        User user = userRepository.findByEmail(email).orElseThrow(()->new RuntimeException("User not found"));
        Alert alert = Alert.builder()
                .symbol(request.getSymbol())
                .triggerPrice(request.getTriggerPrice())
                .direction(request.getDirection().toUpperCase())
                .status("ACTIVE")
                .user(user)
                .build();

        Alert saved = alertRepository.save(alert);
        return mapToResponse(alert);
    }

    @Override
    public List<AlertResponse> getMyAlerts(String email){
        User user = userRepository.findByEmail(email).orElseThrow(()->new RuntimeException("User not found"));
        return alertRepository.findByUser(user)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public List<AlertResponse> getTriggeredAlerts(String email){
        User user = userRepository.findByEmail(email).orElseThrow(()->new RuntimeException("User not found"));
        return alertRepository.findByUser(user)
                .stream()
                .filter(alert->alert.getStatus().equals("TRIGGERED"))
                .map(this::mapToResponse)
                .toList();
    }

    public AlertResponse mapToResponse(Alert alert){
        return AlertResponse.builder()
                .id(alert.getId())
                .symbol(alert.getSymbol())
                .triggerPrice(alert.getTriggerPrice())
                .direction(alert.getDirection())
                .status(alert.getStatus())
                .triggeredAt(alert.getTriggeredAt())
                .build();
    }

    @Scheduled(fixedRate = 30000)
    @Override
    @Transactional
    public void checkAndTriggerAlerts(){
        List<Alert> activeAlerts = alertRepository.findByStatus("ACTIVE");
        for(Alert alert : activeAlerts){
            var price = cryptoPriceRepository.findBySymbol(alert.getSymbol()).orElse(null);
            if(price==null){
                continue;
            }
            boolean shouldTrigger = false;
            if(alert.getDirection().equalsIgnoreCase("ABOVE")){
                shouldTrigger = price.getCurrentPrice().compareTo(alert.getTriggerPrice())>=0;
            }else if(alert.getDirection().equalsIgnoreCase("BELOW")){
                shouldTrigger = price.getCurrentPrice().compareTo(alert.getTriggerPrice())<=0;
            }
            if(shouldTrigger){
                alert.setStatus("TRIGGERED");
                alert.setTriggeredAt(LocalDateTime.now());
                alertRepository.save(alert);
                emailService.sendAlertTriggeredEmail(
                        alert.getUser().getEmail(),
                        alert.getSymbol(),
                        alert.getTriggerPrice().toString()
                );

            }
        }

    }
}
