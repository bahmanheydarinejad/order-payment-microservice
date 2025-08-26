package com.example.common.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record PaymentDto(UUID id, UUID orderId, BigDecimal amount, String status, Instant createdAt) {
}
