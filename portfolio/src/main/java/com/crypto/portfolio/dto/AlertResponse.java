package com.crypto.portfolio.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AlertResponse {

    private Long id;
    private String symbol;
    private BigDecimal triggerPrice;
    private String direction;
    private String status;
    private LocalDateTime triggeredAt;
}
