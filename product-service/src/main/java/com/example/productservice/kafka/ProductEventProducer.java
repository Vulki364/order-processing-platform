package com.example.productservice.kafka;

import com.example.productservice.model.Product;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ProductEventProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public ProductEventProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }


    public void sendProductCreatedEvent(String productJson) {
        kafkaTemplate.send("product.created", productJson);
    }

    public void sendProductUpdatedEvent(String productJson) {
        kafkaTemplate.send("product.updated", productJson);
    }

}
