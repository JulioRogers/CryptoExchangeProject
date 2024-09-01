package com.globant.model.orders;

import com.globant.model.User;
import com.globant.model.currencies.CryptoCurrency;

import java.math.BigDecimal;

public class SellOrder extends Order {

    public SellOrder(User user, CryptoCurrency crypto, BigDecimal amount, BigDecimal price) {
        super(user, crypto, amount, price);
    }
}