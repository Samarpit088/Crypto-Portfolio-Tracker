package com.crypto.portfolio.service;

import com.crypto.portfolio.entity.CryptoPrice;

import java.util.List;

public interface CryptoPriceService {
    void updateSimulatedPrices();
    List<CryptoPrice> getAllPrices();
}
