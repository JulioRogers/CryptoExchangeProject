package com.globant.service.orders;

import com.globant.model.orders.Order;

import java.math.BigDecimal;

public interface OrderService {
    void addOrder(Order order);
    Order getOrder(BigDecimal amount, BigDecimal price);
}