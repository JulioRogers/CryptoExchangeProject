package com.globant.service;

public class InvalidCryptoException extends RuntimeException {
    public InvalidCryptoException(String message) {
        super(message);
    }
}
