package com.crypto.portfolio.service;

import com.crypto.portfolio.dto.AlertRequest;
import com.crypto.portfolio.dto.AlertResponse;

import java.util.List;

public interface AlertService {
    AlertResponse createAlert(AlertRequest request,String email);
    List<AlertResponse> getMyAlerts(String email);
    List<AlertResponse> getTriggeredAlerts(String email);
    void checkAndTriggerAlerts();
}
