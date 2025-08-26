package com.example.common.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record OrderDto(UUID id, String customerId, BigDecimal amount, String status, Instant createdAt) {
}
