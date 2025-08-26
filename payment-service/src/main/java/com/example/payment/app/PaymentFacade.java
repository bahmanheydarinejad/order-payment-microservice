package com.example.payment.app;

import com.example.common.events.PaymentStatusEvent;
import com.example.payment.domain.model.Payment;
import com.example.payment.domain.service.PaymentDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentFacade {

    private final PaymentDomainService domain;
    private final KafkaTemplate<String, PaymentStatusEvent> kafka;

    @Transactional
    public Payment process(UUID orderId, BigDecimal amount, String correlationId) {
        var existing = domain.findByOrderId(orderId);
        if (existing.isPresent()) {
            return existing.get();
        }
        Payment p = new Payment(UUID.randomUUID(), orderId, amount);
        if (amount.compareTo(new BigDecimal("1000")) <= 0) {
            p.paid();
        } else {
            p.fail("Amount exceeds limit");
        }
        p = domain.save(p);
        kafka.send("payment.status", orderId.toString(), new PaymentStatusEvent(orderId, p.getId(), p.getStatus().name(), p.getFailureReason(), correlationId));
        return p;
    }
}
