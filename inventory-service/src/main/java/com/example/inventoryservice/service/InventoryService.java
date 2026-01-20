package com.example.inventoryservice.service;

import com.example.inventoryservice.model.Inventory;
import com.example.inventoryservice.repository.InventoryRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    public InventoryService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    public List<Inventory> getAllInventory() {
        return inventoryRepository.findAll();
    }

    public Optional<Inventory> getInventoryByProductId(Long productId) {
        return inventoryRepository.findByProductId(productId);
    }

    public Inventory saveInventory(Inventory inventory) {
        inventory.setCreatedAt(LocalDateTime.now());
        inventory.setUpdatedAt(LocalDateTime.now());
        return inventoryRepository.save(inventory);
    }

    public boolean reserveProduct(Long productId, int quantity) {
        Optional<Inventory> inventoryOpt = inventoryRepository.findByProductId(productId);
        if (inventoryOpt.isEmpty()) return false;

        Inventory inventory = inventoryOpt.get();

        if (inventory.getQuantityAvailable() < quantity) {
            return false;
        }

    inventory.setQuantityAvailable(inventory.getQuantityAvailable() - quantity);
        inventory.setQuantityReserved(inventory.getQuantityReserved() + quantity);
        inventoryRepository.save(inventory);

        return true;
    }

    public boolean releaseProduct(Long productId, int quantity) {
    Optional<Inventory> inventoryOpt = inventoryRepository.findByProductId(productId);
    if (inventoryOpt.isEmpty()) return false;

    Inventory inventory = inventoryOpt.get();

    inventory.setQuantityReserved(inventory.getQuantityAvailable() + quantity);
    inventory.setQuantityAvailable(inventory.getQuantityReserved() - quantity);
    inventoryRepository.save(inventory);

    return true;


    }
}

