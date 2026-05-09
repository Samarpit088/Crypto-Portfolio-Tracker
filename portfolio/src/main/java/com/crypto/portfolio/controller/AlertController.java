package com.crypto.portfolio.controller;

import com.crypto.portfolio.dto.AlertRequest;
import com.crypto.portfolio.dto.AlertResponse;
import com.crypto.portfolio.service.AlertService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/alerts")
public class AlertController {
    private final AlertService alertService;

    @PostMapping("/create")
    public ResponseEntity<AlertResponse> createAlert(@Valid @RequestBody AlertRequest request, Authentication authentication){
        String email = authentication.getName();
        return ResponseEntity.status(HttpStatus.CREATED).body(alertService.createAlert(request,email));
    }

    @GetMapping("/my")
    public ResponseEntity<List<AlertResponse>> getMyAlerts(Authentication authentication){
        return ResponseEntity.ok(alertService.getMyAlerts(authentication.getName()));
    }

    @GetMapping("/triggered")
    public ResponseEntity<List<AlertResponse>> getTriggeredAlerts(Authentication authentication){
        return ResponseEntity.ok(alertService.getTriggeredAlerts(authentication.getName()));
    }
}
