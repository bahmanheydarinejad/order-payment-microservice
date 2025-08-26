package com.example.common.events;

import java.util.UUID;

public record PaymentStatusEvent(UUID orderId, UUID paymentId, String status, String reason, String correlationId) {
}
