package com.globant.service.orders;

import com.globant.exceptions.OrderNotFoundException;
import com.globant.model.orders.Order;
import com.globant.model.orders.SellOrder;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.Queue;

public class SellOrderService implements OrderService {
    private final Queue<SellOrder> sellOrders = new LinkedList<>();

    @Override
    public void addOrder(Order order) {
        if (order instanceof SellOrder) {
            sellOrders.offer((SellOrder) order);
        }
    }

    @Override
    public Order getOrder(BigDecimal amount, BigDecimal price) {
        for (SellOrder sellOrder : sellOrders) {
            if (sellOrder.getAmount().compareTo(amount) == 0 && sellOrder.getPrice().compareTo(price) <=0){
                return sellOrder;
            }
        }
        throw new OrderNotFoundException("No sell order found");
    }

}