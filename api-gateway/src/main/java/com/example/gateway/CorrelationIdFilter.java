package com.example.gateway;

import com.example.common.tracing.AppConstants;
import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

@Component
public class CorrelationIdFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        HttpHeaders headers = exchange.getRequest().getHeaders();
        AtomicReference<String> correlationId = new AtomicReference<>(headers.getFirst(AppConstants.HEADER));

        if (StringUtils.isBlank(correlationId.get())) {
            correlationId.set(UUID.randomUUID().toString());
            exchange = exchange.mutate()
                    .request(r -> r.headers(h -> h.set(AppConstants.HEADER, correlationId.get())))
                    .build();
        }

        return chain.filter(exchange)
                .contextWrite(ctx -> ctx.put(AppConstants.HEADER, correlationId.get()));
    }

    @Override
    public int getOrder() {
        return -100;
    }
}

