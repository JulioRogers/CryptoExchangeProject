package com.globant.model.orders;

import com.globant.model.User;

import java.math.BigDecimal;

public class BuyOrder extends Order {

    public BuyOrder(User user, String crypto, BigDecimal amount, BigDecimal price){
        super(user, crypto, amount, price);
    }
}
