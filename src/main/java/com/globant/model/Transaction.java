package com.globant.model;

import com.globant.model.currencies.CryptoCurrency;

import java.math.BigDecimal;

public class Transaction {
    CryptoCurrency cryptoCurrency;
    BigDecimal amount;
    BigDecimal price;
    String orderType;

    public Transaction(CryptoCurrency cryptoCurrency, BigDecimal amount, BigDecimal price, String orderType) {
        this.cryptoCurrency = cryptoCurrency;
        this.amount = amount;
        this.price = price;
        this.orderType = orderType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public CryptoCurrency getCryptoCurrency() {
        return cryptoCurrency;
    }

    public String getOrderType() {
        return orderType;
    }
}