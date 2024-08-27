package com.globant.model;

public class NegativeDepositException extends RuntimeException {
    public NegativeDepositException(String message) {
        super(message);
    }
}
