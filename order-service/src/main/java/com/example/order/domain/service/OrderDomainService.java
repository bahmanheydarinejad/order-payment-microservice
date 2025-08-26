package com.example.order.domain.service;

import com.example.common.exceptions.BusinessException;
import com.example.order.domain.model.Order;
import com.example.order.domain.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderDomainService {
    private final OrderRepository repo;

    @Transactional
    public Order create(Order o) {
        return repo.save(o);
    }

    @Transactional(readOnly = true)
    public Order get(UUID id) {
        return repo.findById(id).orElseThrow(() -> new BusinessException("order.not.found", id));
    }

    @Transactional(readOnly = true)
    public Page<Order> list(Pageable p) {
        return repo.findAll(p);
    }

    @Transactional
    public Order save(Order o) {
        return repo.save(o);
    }
}
