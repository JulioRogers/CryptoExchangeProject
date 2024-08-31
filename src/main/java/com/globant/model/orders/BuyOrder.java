package com.globant.model.orders;

import com.globant.model.User;
import com.globant.model.currencies.CryptoCurrency;

import java.math.BigDecimal;

public class BuyOrder extends Order {

    public BuyOrder(User user, CryptoCurrency crypto, BigDecimal amount, BigDecimal price){
        super(user, crypto, amount, price);
    }
}
