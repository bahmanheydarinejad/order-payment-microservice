package com.example.gateway;

import com.example.common.tracing.AppConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class LoggingFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, org.springframework.cloud.gateway.filter.GatewayFilterChain chain) {
        long startTime = System.currentTimeMillis();

        String path = exchange.getRequest().getURI().getPath();
        String correlationId = Optional.of(exchange.getRequest().getHeaders().get(AppConstants.HEADER)).filter(l -> !CollectionUtils.isEmpty(l)).map(List::getFirst).orElse("-");
        String method = exchange.getRequest().getMethod().name();

        log.info("Correlation Id: {}\t\t<== Incoming request: [{} {}]", correlationId, method, path);

        return chain.filter(exchange).then(Mono.fromRunnable(() -> {
            long duration = System.currentTimeMillis() - startTime;
            exchange.getResponse().getStatusCode();
            HttpStatusCode statusCode = exchange.getResponse().getStatusCode();
            log.info("Correlation Id: {}\t\t==> Response for [{} {}] -> {} ({} ms)", correlationId, method, path, statusCode, duration);
        }));
    }

    @Override
    public int getOrder() {
        return -50;
    }
}
