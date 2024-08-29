package com.globant.exceptions;

public class InvalidCryptoException extends RuntimeException {
    public InvalidCryptoException(String message) {
        super(message);
    }
}
