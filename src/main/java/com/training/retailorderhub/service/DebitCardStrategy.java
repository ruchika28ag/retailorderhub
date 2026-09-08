package com.training.retailorderhub.service;

import org.springframework.stereotype.Component;

@Component("DEBIT_CARD")
public class DebitCardStrategy implements PaymentStrategy {

    @Override
    public boolean charge(double amount) {
        System.out.println("Charging debit card: " + amount);
        return true;
    }
}
