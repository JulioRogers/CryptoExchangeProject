package com.globant.exceptions;

public class NegativeAmountException extends RuntimeException {
    public NegativeAmountException() {
        super("Amount must be greater than zero");
    }
}
