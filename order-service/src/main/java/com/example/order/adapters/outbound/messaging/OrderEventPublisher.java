package com.example.order.adapters.outbound.messaging;

import com.example.common.events.OrderCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderEventPublisher {

    private final KafkaTemplate<String, OrderCreatedEvent> kafka;

    public void publish(OrderCreatedEvent evt) {
        kafka.send("order.created", evt.orderId().toString(), evt);
    }

}
