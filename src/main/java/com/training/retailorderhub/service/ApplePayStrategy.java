package com.training.retailorderhub.service;

import org.springframework.stereotype.Component;

@Component("APPLE_PAY")
public class ApplePayStrategy implements PaymentStrategy {

    @Override
    public boolean charge(double amount) {
        System.out.println("Charging apple pay: " + amount);
        return true;
    }
}
