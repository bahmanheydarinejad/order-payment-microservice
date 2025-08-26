package com.example.payment.adapters.inbound.messaging;

import com.example.common.events.OrderCreatedEvent;
import com.example.payment.app.PaymentFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import static com.example.common.tracing.AppConstants.HEADER;

@Component
@RequiredArgsConstructor
public class OrderEventsListener {

    private final PaymentFacade facade;

    @KafkaListener(topics = "order.created", groupId = "payment-service")
    public void onOrderCreated(OrderCreatedEvent evt, @Header(name = HEADER, required = false) String cid) {
        facade.process(evt.orderId(), evt.amount(), evt.correlationId() != null ? evt.correlationId() : cid);
    }

}
