package com.globant.model;

import java.math.BigDecimal;

public class SellOrder extends Order {

    public SellOrder(String crypto, BigDecimal amount, BigDecimal price) {
        super(crypto, amount, price);
    }
}