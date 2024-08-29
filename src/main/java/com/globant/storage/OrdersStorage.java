package com.globant.storage;

import com.globant.model.Order;

import java.math.BigDecimal;

public interface OrdersStorage {
    void addOrder(Order order);
    Order getOrder(BigDecimal amount, BigDecimal price);
}
