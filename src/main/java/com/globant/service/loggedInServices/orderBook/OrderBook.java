package com.globant.service.loggedInServices.orderBook;

import com.globant.model.currencies.CryptoCurrency;
import com.globant.model.orders.Order;

import java.math.BigDecimal;

public interface OrderBook {
    void addOrder(Order order);
    Order getOrder(BigDecimal amount, BigDecimal price, CryptoCurrency crypto);
}