package com.crypto.portfolio.repository;

import com.crypto.portfolio.entity.Alert;
import com.crypto.portfolio.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlertRepository extends JpaRepository<Alert,Long> {
    List<Alert> findByUser(User user);
    List<Alert> findByUserId(Long userId);
    List<Alert> findByStatus(String status);
}
