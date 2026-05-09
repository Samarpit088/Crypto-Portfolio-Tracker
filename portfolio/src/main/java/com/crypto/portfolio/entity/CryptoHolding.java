package com.crypto.portfolio.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name="crypto_holdings")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CryptoHolding {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String coinName;

    @Column(nullable = false)
    private String symbol;

    @Column(nullable = false,precision = 19,scale = 8)
    private BigDecimal quantityHeld;

    @Column(nullable = false,precision = 19,scale = 8)
    private BigDecimal buyPrice;

    @Column(nullable = false)
    private LocalDate buyDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="user_id",nullable=false)
    private User user;
}
