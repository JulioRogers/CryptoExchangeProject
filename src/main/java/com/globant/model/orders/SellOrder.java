package com.globant.model.orders;

import com.globant.model.User;

import java.math.BigDecimal;

public class SellOrder extends Order {

    public SellOrder(User user, String crypto, BigDecimal amount, BigDecimal price) {
        super(user, crypto, amount, price);
    }
}