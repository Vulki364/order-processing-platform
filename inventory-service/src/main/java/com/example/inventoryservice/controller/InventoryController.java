package com.example.inventoryservice.controller;

import com.example.inventoryservice.model.Inventory;
import com.example.inventoryservice.service.InventoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping
    public List<Inventory> getAllInventory() {
        return inventoryService.getAllInventory();
    }

    @GetMapping("/{productId}")
    public Inventory getInventoryByProductId(@PathVariable Long productId) {
        return inventoryService.getInventoryByProductId(productId)
                .orElseThrow(() -> new RuntimeException("Inventory not found by product id " + productId));
    }

    @PostMapping
    public Inventory saveInventory(@RequestBody Inventory inventory) {
        return inventoryService.saveInventory(inventory);
    }

    @PostMapping("/{productId}/reserve")
    public String reserveProduct(@PathVariable Long productId, @RequestParam int quantity) {
        boolean success = inventoryService.reserveProduct(productId, quantity);
        return success ? "Released " + quantity + " items" : "Inventory not found";
    }
}
