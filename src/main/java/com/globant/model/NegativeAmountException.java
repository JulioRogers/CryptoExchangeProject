package com.globant.model;

public class NegativeAmountException extends RuntimeException {
    public NegativeAmountException() {
        super("Amount must be greater than zero");
    }
}
