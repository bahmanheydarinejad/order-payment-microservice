# Order & Payment Microservices — Spring Cloud

Java 21, Spring Boot 3.5.3, Spring Cloud 2025.0.0. Two services (order, payment) + Kafka events,
PostgreSQL with Flyway, Eureka discovery, and Spring Cloud Gateway.

## Quickstart

```bash
mvn -q -DskipTests clean package
docker compose up -d --build
# create order
curl -X POST http://localhost:8080/orders -H 'Content-Type: application/json' -d '{"customerId":"c-100","amount":99.99}'
# list
curl http://localhost:8080/orders
curl http://localhost:8080/payments
```

## Modules

- discovery (Eureka)
- api-gateway (Gateway)
- order-service (REST + Kafka producer + payment.status consumer)
- payment-service (REST + Kafka consumer + payment.status producer)
- common-lib (DTOs & events)

## dashboards

- Eureka service discovery accessible at: http://localhost:8761
- kafka-ui accessible at: http://localhost:9091
- pgAdmin accessible at: http://localhost:5432 with username: "bahmanheydarinejad@gmail.com" and password "
  bahmanheydarinejad"
- Order swagger-ui accessible at: http://localhost:8081/documents
- Payment swagger-ui accessible at: http://localhost:8082/documents

[Bahman Heydarinejad - GitHub](https://github.com/bahmanheydarinejad)
