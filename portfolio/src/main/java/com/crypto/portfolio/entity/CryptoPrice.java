package com.crypto.portfolio.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name="crypto_prices")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CryptoPrice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,unique = true)
    private String symbol;

    @Column(nullable = false,precision = 19,scale=8)
    private BigDecimal currentPrice;

    @Column(nullable = false)
    private LocalDateTime timestamp;
}
