package com.example.order.adapters.inbound.http;

import com.example.common.dto.OrderDto;
import com.example.order.app.OrderFacade;
import com.example.order.domain.model.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static com.example.common.tracing.AppConstants.HEADER;

@RestController
@RequestMapping(path = "/orders")
@RequiredArgsConstructor
public class OrdersController {

    private final OrderFacade facade;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public OrderDto create(@RequestBody CreateOrderRequest req, @RequestHeader(value = HEADER, required = false) String cid) {
        Order o = facade.place(req.customerId(), req.amount(), cid);
        return new OrderDto(o.getId(), o.getCustomerId(), o.getAmount(), o.getStatus().name(), o.getCreatedAt());
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public OrderDto get(@PathVariable UUID id) {
        Order o = facade.get(id);
        return new OrderDto(o.getId(), o.getCustomerId(), o.getAmount(), o.getStatus().name(), o.getCreatedAt());
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<OrderDto> list(@RequestParam(name = "page", defaultValue = "0") int page, @RequestParam(name = "size", defaultValue = "20") int size) {
        return facade.list(PageRequest.of(page, size)).map(o -> new OrderDto(o.getId(), o.getCustomerId(), o.getAmount(), o.getStatus().name(), o.getCreatedAt())).getContent();
    }

    @PutMapping(path = "/{id}/status", produces = MediaType.APPLICATION_JSON_VALUE)
    public OrderDto update(@PathVariable UUID id, @RequestParam String status) {
        Order o = facade.updateStatus(id, status.toUpperCase());
        return new OrderDto(o.getId(), o.getCustomerId(), o.getAmount(), o.getStatus().name(), o.getCreatedAt());
    }

    public record CreateOrderRequest(String customerId, BigDecimal amount) {
    }

}
