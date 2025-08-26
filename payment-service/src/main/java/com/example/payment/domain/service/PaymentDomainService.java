package com.example.payment.domain.service;

import com.example.common.exceptions.BusinessException;
import com.example.payment.domain.model.Payment;
import com.example.payment.domain.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentDomainService {

    private final PaymentRepository repo;

    @Transactional
    public Payment save(Payment p) {
        return repo.save(p);
    }

    @Transactional(readOnly = true)
    public Payment get(UUID id) {
        return repo.findById(id).orElseThrow(() -> new BusinessException("payment.not.found", id));
    }

    @Transactional(readOnly = true)
    public Page<Payment> list(Pageable p) {
        return repo.findAll(p);
    }

    @Transactional(readOnly = true)
    public Optional<Payment> findByOrderId(UUID orderId) {
        return repo.findByOrderId(orderId);
    }

}
