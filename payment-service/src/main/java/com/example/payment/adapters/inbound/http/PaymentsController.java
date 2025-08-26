package com.example.payment.adapters.inbound.http;

import com.example.common.dto.PaymentDto;
import com.example.payment.app.PaymentFacade;
import com.example.payment.domain.model.Payment;
import com.example.payment.domain.service.PaymentDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static com.example.common.tracing.AppConstants.HEADER;

@RestController
@RequestMapping(path = "/payments")
@RequiredArgsConstructor
public class PaymentsController {

    private final PaymentFacade facade;
    private final PaymentDomainService domain;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public PaymentDto create(@RequestBody CreatePaymentRequest req, @RequestHeader(value = HEADER, required = false) String cid) {
        Payment p = facade.process(req.orderId(), req.amount(), cid);
        return new PaymentDto(p.getId(), p.getOrderId(), p.getAmount(), p.getStatus().name(), p.getCreatedAt());
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public PaymentDto get(@PathVariable UUID id) {
        Payment p = domain.get(id);
        return new PaymentDto(p.getId(), p.getOrderId(), p.getAmount(), p.getStatus().name(), p.getCreatedAt());
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<PaymentDto> list(@RequestParam(name = "page", defaultValue = "0") int page, @RequestParam(name = "size", defaultValue = "20") int size) {
        return domain.list(PageRequest.of(page, size)).map(p -> new PaymentDto(p.getId(), p.getOrderId(), p.getAmount(), p.getStatus().name(), p.getCreatedAt())).getContent();
    }

    @PutMapping(path = "/{id}/status", produces = MediaType.APPLICATION_JSON_VALUE)
    public PaymentDto update(@PathVariable UUID id, @RequestParam String status) {
        Payment p = domain.get(id);
        switch (status.toUpperCase()) {
            case "PAID" -> p.paid();
            case "FAILED" -> p.fail("manual");
            default -> {
            }
        }
        p = domain.save(p);
        return new PaymentDto(p.getId(), p.getOrderId(), p.getAmount(), p.getStatus().name(), p.getCreatedAt());
    }

    public record CreatePaymentRequest(UUID orderId, BigDecimal amount) {
    }

}
