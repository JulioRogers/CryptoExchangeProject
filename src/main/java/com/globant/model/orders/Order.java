package com.globant.model.orders;

import java.math.BigDecimal;

public abstract class Order {
    private final String crypto;
    private final BigDecimal amount;
    private final BigDecimal price;

    public Order(String crypto, BigDecimal amount, BigDecimal price) {
        this.crypto = crypto;
        this.amount = amount;
        this.price = price;
    }
    public String getCrypto() {
        return crypto;
    }
    public BigDecimal getAmount() {
        return amount;
    }
    public BigDecimal getPrice() {
        return price;
    }
}