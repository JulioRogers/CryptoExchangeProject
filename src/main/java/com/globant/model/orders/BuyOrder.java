package com.globant.model.orders;

import java.math.BigDecimal;

public class BuyOrder extends Order {

    public BuyOrder(String crypto, BigDecimal amount, BigDecimal price){
        super(crypto, amount, price);
    }
}
