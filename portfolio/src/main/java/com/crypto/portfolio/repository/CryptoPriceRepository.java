package com.crypto.portfolio.repository;

import com.crypto.portfolio.entity.CryptoPrice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CryptoPriceRepository extends JpaRepository<CryptoPrice,Long> {
    Optional<CryptoPrice> findBySymbol(String symbol);
    boolean existsBySymbol(String symbol);
}
