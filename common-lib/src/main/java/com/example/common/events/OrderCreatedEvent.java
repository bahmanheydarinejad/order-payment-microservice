package com.example.common.events;

import java.math.BigDecimal;
import java.util.UUID;

public record OrderCreatedEvent(UUID orderId, String customerId, BigDecimal amount, String correlationId) {
}
