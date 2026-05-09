package com.crypto.portfolio.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AlertRequest {

    @NotBlank(message = "Symbol is required")
    private String symbol;

    @NotNull(message = "Trigger price is required")
    @Positive(message = "Trigger price must be greater than 0")
    private BigDecimal triggerPrice;

    @NotBlank(message = "Direction is required")
    private String direction;
}
