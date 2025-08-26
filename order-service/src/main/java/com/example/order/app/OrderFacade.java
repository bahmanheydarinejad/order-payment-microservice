package com.example.order.app;

import com.example.common.events.OrderCreatedEvent;
import com.example.order.adapters.outbound.messaging.OrderEventPublisher;
import com.example.order.domain.model.Order;
import com.example.order.domain.service.OrderDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderFacade {

    private final OrderDomainService domain;
    private final OrderEventPublisher publisher;

    @Transactional
    public Order place(String customerId, BigDecimal amount, String correlationId) {
        Order order = new Order(UUID.randomUUID(), customerId, amount);
        order = domain.create(order);
        publisher.publish(new OrderCreatedEvent(order.getId(), customerId, amount, correlationId));
        return order;
    }

    public Order get(UUID id) {
        return domain.get(id);
    }

    public Page<Order> list(Pageable p) {
        return domain.list(p);
    }

    public Order updateStatus(UUID id, String status) {
        Order o = domain.get(id);
        switch (status) {
            case "PAID" -> o.markPaid();
            case "FAILED" -> o.markFailed();
            default -> {
            }
        }
        return domain.save(o);
    }
}
