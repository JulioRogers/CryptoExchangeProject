package com.globant.model.orders;

import com.globant.model.User;
import com.globant.model.currencies.CryptoCurrency;

import java.math.BigDecimal;

public abstract class Order {
    private final CryptoCurrency crypto;
    private final BigDecimal amount;
    private final BigDecimal price;
    private final User user;

    public Order(User user, CryptoCurrency crypto, BigDecimal amount, BigDecimal price) {
        this.user = user;
        this.crypto = crypto;
        this.amount = amount;
        this.price = price;
    }

    public User getUser() {
        return user;
    }
    public CryptoCurrency getCrypto() {
        return crypto;
    }
    public BigDecimal getAmount() {
        return amount;
    }
    public BigDecimal getPrice() {
        return price;
    }
}