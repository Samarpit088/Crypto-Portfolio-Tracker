package com.crypto.portfolio.controller;

import com.crypto.portfolio.dto.CryptoHoldingRequest;
import com.crypto.portfolio.dto.CryptoHoldingResponse;
import com.crypto.portfolio.service.CryptoHoldingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/portfolio")
@RequiredArgsConstructor
public class CryptoHoldingController {
    private final CryptoHoldingService cryptoHoldingService;

    @PostMapping("/add")
    public ResponseEntity<CryptoHoldingResponse> addHolding(@Valid @RequestBody CryptoHoldingRequest request, Authentication authentication){
        String email = authentication.getName();
        return ResponseEntity.status(HttpStatus.CREATED).body(cryptoHoldingService.addHolding(request,email));
    }


    @GetMapping("/my")
    public ResponseEntity<List<CryptoHoldingResponse>> getMyHoldings(Authentication authentication){
        String email = authentication.getName();
        return ResponseEntity.ok(cryptoHoldingService.getMyHoldings(email));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<CryptoHoldingResponse> updateHolding(@PathVariable Long id,@Valid @RequestBody CryptoHoldingRequest request,Authentication authentication){
        String email = authentication.getName();
        return ResponseEntity.ok(cryptoHoldingService.updateHolding(id,request,email));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteHolding(@PathVariable Long id,Authentication authentication){
        String email = authentication.getName();
        cryptoHoldingService.deleteHolding(id,email);
        return ResponseEntity.ok("Holding deleted successfully");
    }

    @GetMapping("/my-page")
    public ResponseEntity<Page<CryptoHoldingResponse>> getMyHoldingsPaginated(@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "5") int size, Authentication authentication){
        return ResponseEntity.ok(cryptoHoldingService.getMyHoldingsPaginated(authentication.getName(), page, size));
    }

}
