package com.training.retailorderhub.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.training.retailorderhub.model.Order;
import com.training.retailorderhub.repository.OrderRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

/**
 * TRAINING NOTE: This class is deliberately written the way a real legacy class
 * often looks — one method doing everything, duplicated validation, and unsafe
 * query building. It is the reference "OrderManager" (now OrderService) used in
 * Day 1's Lab 1 (HLD vs LLD) and Lab 2 (SonarCloud) materials. Do NOT use this
 * class as a model for production code — Day 2 refactors it through the SOLID
 * principles.
 */
@Service
public class OrderService {

    @PersistenceContext
    private EntityManager entityManager;

    private final OrderRepository orderRepository;
    private final PaymentService paymentService;
    private final InventoryService inventoryService;

    public OrderService(OrderRepository orderRepository, PaymentService paymentService, InventoryService inventoryService) {
        this.orderRepository = orderRepository;
        this.paymentService = paymentService;
        this.inventoryService = inventoryService;
    }

    @Transactional
    public boolean processOrder(String customerId, List<String> itemNames, String paymentMethod, double amount) {
        // Validate customer
        if (customerId == null || customerId.isEmpty()) {
            System.out.println("Invalid customer");
            return false;
        }
        if (itemNames == null || itemNames.isEmpty()) {
            System.out.println("Invalid items");
            return false;
        }

        // Check inventory
        for (String itemName : itemNames) {
            if (!inventoryService.isInStock(itemName)) {
                System.out.println("Out of stock: " + itemName);
                return false;
            }
        }

        // Process payment
        if (!paymentService.charge(paymentMethod, amount)) {
            return false;
        }

        // Save order
        Order order = new Order();
        order.setCustomerId(customerId);
        order.setItemNames(String.join(",", itemNames));
        order.setPaymentMethod(paymentMethod);
        order.setAmount(amount);
        order.setStatus("PENDING");
        order.setCreatedAt(LocalDateTime.now());
        orderRepository.save(order);

        // Update inventory
        for (String itemName : itemNames) {
            inventoryService.decrementQuantity(itemName);
        }

        System.out.println("Order confirmed for customer " + customerId);
        return true;
    }

}
