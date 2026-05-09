package com.crypto.portfolio.repository;

import com.crypto.portfolio.entity.CryptoHolding;
import com.crypto.portfolio.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CryptoHoldingRepository extends JpaRepository<CryptoHolding,Long> {
    List<CryptoHolding> findByUser(User user);
    List<CryptoHolding> findByUserId(Long userId);
    Page<CryptoHolding> findByUser(User user, Pageable pageable);
}
