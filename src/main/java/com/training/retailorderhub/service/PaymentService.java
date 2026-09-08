package com.training.retailorderhub.service;

import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    private final Map<String, PaymentStrategy> strategies;

    public PaymentService(Map<String, PaymentStrategy> strategies) {
        this.strategies = strategies;
    }

    public boolean charge(String paymentMethod, double amount) {
        PaymentStrategy strategy = strategies.get(paymentMethod);
        if (strategy == null) {
            System.out.println("Unknown payment method: " + paymentMethod);
            return false;
        }
        return strategy.charge(amount);
    }
}
