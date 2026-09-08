package com.training.retailorderhub.service;

public interface PaymentStrategy {
    boolean charge(double amount);
}
