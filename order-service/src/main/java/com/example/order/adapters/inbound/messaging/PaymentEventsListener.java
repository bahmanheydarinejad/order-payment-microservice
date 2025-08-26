package com.example.order.adapters.inbound.messaging;

import com.example.common.events.PaymentStatusEvent;
import com.example.order.app.OrderFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentEventsListener {

    private final OrderFacade facade;

    @KafkaListener(topics = "payment.status", groupId = "order-service")
    public void onPaymentStatus(PaymentStatusEvent evt) {
        facade.updateStatus(evt.orderId(), evt.status());
    }

}
