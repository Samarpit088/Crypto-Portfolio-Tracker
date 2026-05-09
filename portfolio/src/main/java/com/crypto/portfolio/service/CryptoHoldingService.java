package com.crypto.portfolio.service;

import com.crypto.portfolio.dto.CryptoHoldingRequest;
import com.crypto.portfolio.dto.CryptoHoldingResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CryptoHoldingService {
    CryptoHoldingResponse addHolding(CryptoHoldingRequest request, String email);
    List<CryptoHoldingResponse> getMyHoldings(String email);
    CryptoHoldingResponse updateHolding(Long id,CryptoHoldingRequest request,String email);
    void deleteHolding(Long id,String email);
    Page<CryptoHoldingResponse> getMyHoldingsPaginated(String email, int page, int size);
}
