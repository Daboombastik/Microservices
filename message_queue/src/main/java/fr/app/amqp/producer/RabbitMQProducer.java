package fr.app.amqp.producer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public record RabbitMQProducer<T> (AmqpTemplate mainProducer) {

    public void publish(String exchange, String routingKey, T payload) {
        mainProducer.convertAndSend(exchange, routingKey, payload);
        log.info("Published to {} using routingKey {}. Payload: {}", exchange, routingKey, payload);
    }
}
