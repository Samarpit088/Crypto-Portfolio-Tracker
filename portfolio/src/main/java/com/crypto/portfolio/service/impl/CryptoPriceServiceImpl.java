package com.crypto.portfolio.service.impl;

import com.crypto.portfolio.entity.CryptoPrice;
import com.crypto.portfolio.repository.CryptoPriceRepository;
import com.crypto.portfolio.service.CryptoPriceService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class CryptoPriceServiceImpl implements CryptoPriceService {
    private final CryptoPriceRepository cryptoPriceRepository;
    private final Random random = new Random();

    @Scheduled(fixedRate = 30000)
    @Override
    public void updateSimulatedPrices(){
        updatePrice("BTC",65000);
        updatePrice("ETH",3200);
        updatePrice("SOL",150);
        updatePrice("ADA",0.45);
    }
    private void updatePrice(String symbol,double basePrice){
        double variation = (random.nextDouble()-0.5)*0.1;
        double newPrice = basePrice+(basePrice*variation);
        CryptoPrice price = cryptoPriceRepository.findBySymbol(symbol)
                .orElse(CryptoPrice.builder().symbol(symbol).build());
        price.setCurrentPrice(BigDecimal.valueOf(newPrice));
        price.setTimestamp(LocalDateTime.now());
        cryptoPriceRepository.save(price);
    }
    @Override
    public List<CryptoPrice> getAllPrices(){
        return cryptoPriceRepository.findAll();
    }
}
