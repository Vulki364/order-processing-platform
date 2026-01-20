package com.example.productservice.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.productservice.model.Product;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

@Service
public class ProductEventProducer {

    private static final Logger log = LoggerFactory.getLogger(ProductEventProducer.class);
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public ProductEventProducer(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }


    public void sendProductCreatedEvent(Product product) {
        try {
            String productJson = objectMapper.writeValueAsString(product);
            kafkaTemplate.send("product.created", productJson);
            log.info("Kafka event sent for product id={}", product.getId());
        } catch (Exception e) {
            log.error("Failed to serialize product for Kafka: {}", product, e);
        }
    }

    public void sendProductUpdatedEvent(String productJson) {
        log.info("sending product updated event{}", productJson);
        kafkaTemplate.send("product.updated", productJson);
    }

}
