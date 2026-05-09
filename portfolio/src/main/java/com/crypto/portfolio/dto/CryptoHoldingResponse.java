package com.crypto.portfolio.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CryptoHoldingResponse {

    private Long id;
    private String coinName;
    private String symbol;
    private BigDecimal quantityHeld;
    private BigDecimal buyPrice;
    private LocalDate buyDate;
    private BigDecimal currentPrice;
    private BigDecimal currentValue;
    private BigDecimal profitLoss;
}
