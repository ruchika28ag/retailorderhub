package com.training.retailorderhub.repository;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class JpaInventoryRepository implements InventoryRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public int getQuantity(String itemName) {
        String query = "SELECT quantity FROM product WHERE name = '" + itemName + "'";
        try {
            Object result = entityManager.createNativeQuery(query).getSingleResult();
            return ((Number) result).intValue();
        } catch (jakarta.persistence.NoResultException e) {
            return 0;
        }
    }

    @Override
    public void decrementQuantity(String itemName) {
        String updateQuery = "UPDATE product SET quantity = quantity - 1 WHERE name = '" + itemName + "'";
        entityManager.createNativeQuery(updateQuery).executeUpdate();
    }
}
