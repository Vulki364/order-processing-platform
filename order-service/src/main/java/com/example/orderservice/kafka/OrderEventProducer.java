package com.example.orderservice.kafka;

import com.example.orderservice.event.OrderCreatedEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class OrderEventProducer {

    private static final String TOPIC = "order-created";

    private final KafkaTemplate<String, String> kafkaTemplate;

    public OrderEventProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendOrderCreatedEvent(OrderCreatedEvent event) {
        String message = event.getOrderId() + "," +
                         event.getProductId() + "," +
                         event.getQuantity();

        kafkaTemplate.send(TOPIC, message);
    }
}
