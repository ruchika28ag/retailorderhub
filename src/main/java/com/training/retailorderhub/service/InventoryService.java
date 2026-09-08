package com.training.retailorderhub.service;

import org.springframework.stereotype.Service;

import com.training.retailorderhub.repository.InventoryRepository;

@Service
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    public InventoryService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    public boolean isInStock(String itemName) {
        return inventoryRepository.getQuantity(itemName) > 0;
    }

    public void decrementQuantity(String itemName) {
        inventoryRepository.decrementQuantity(itemName);
    }
}
