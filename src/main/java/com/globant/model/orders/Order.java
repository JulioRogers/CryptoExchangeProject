package com.globant.model.orders;

import com.globant.model.User;

import java.math.BigDecimal;

public abstract class Order {
    private final String crypto;
    private final BigDecimal amount;
    private final BigDecimal price;
    private final User user;

    public Order(User user, String crypto, BigDecimal amount, BigDecimal price) {
        this.user = user;
        this.crypto = crypto;
        this.amount = amount;
        this.price = price;
    }

    public User getUser() {
        return user;
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