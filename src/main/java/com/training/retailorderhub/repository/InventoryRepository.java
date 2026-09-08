package com.training.retailorderhub.repository;

public interface InventoryRepository {

    int getQuantity(String itemName);

    void decrementQuantity(String itemName);
}
