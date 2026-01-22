package com.example.inventoryservice.kafka;

import com.example.inventoryservice.dto.ProductDto;
import com.example.inventoryservice.model.Inventory;
import com.example.inventoryservice.service.InventoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

@Component
public class ProductEventListener {

    private static final Logger log = LoggerFactory.getLogger(ProductEventListener.class);
    private final InventoryService inventoryService;
    private final ObjectMapper objectMapper;

    public ProductEventListener(InventoryService inventoryService, ObjectMapper objectMapper) {
        this.inventoryService = inventoryService;
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "product.created", groupId = "inventory-service")
    public void handleProductCreated(String message) {
        log.info("Received product.created event: {}", message);
        try {
            ProductDto product = objectMapper.readValue(message, ProductDto.class);

            Inventory inventory = new Inventory(
                    product.getId(),
                    0,
                    0,
                    null,
                    null
            );
            inventoryService.saveInventory(inventory);
            log.info("Inventory created for productId={}", product.getId());
        } catch (Exception e) {
            log.error("Failed to process product.created event", e);
        }
    }
}
