package com.crypto.portfolio.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CryptoHoldingRequest {

    @NotBlank(message = "Coin name is required")
    private String coinName;

    @NotBlank(message = "Symbol is required")
    private String symbol;

    @NotNull(message = "Quantity is required")
    @Positive(message = "Quantity must be greater than 0")
    private BigDecimal quantityHeld;

    @NotNull(message = "Buy price is required")
    @Positive(message = "Buy price must be greater than 0")
    private BigDecimal buyPrice;

    @NotNull(message = "Buy date is required")
    private LocalDate buyDate;
}
