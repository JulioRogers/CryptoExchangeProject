package com.globant.model;

import java.math.BigDecimal;

public interface OrdersStorage {
    void addOrder(Order order);
    Order getOrder(BigDecimal amount, BigDecimal price);
}
