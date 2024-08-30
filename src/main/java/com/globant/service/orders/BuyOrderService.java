package com.globant.service.orders;

import com.globant.exceptions.OrderNotFoundException;
import com.globant.model.User;
import com.globant.model.currencies.CryptoCurrency;
import com.globant.model.orders.BuyOrder;
import com.globant.model.orders.Order;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.Queue;

public class BuyOrderService implements OrderService {
    private final Queue<BuyOrder> buyOrders = new LinkedList<>();

    @Override
    public void addOrder(Order order) {
        if (order instanceof BuyOrder) {
            buyOrders.offer((BuyOrder) order);
        }
    }

    @Override
    public Order getOrder(BigDecimal amount, BigDecimal price) {
        for (BuyOrder buyOrder : buyOrders) {
            if (buyOrder.getAmount().compareTo(amount) == 0 && buyOrder.getPrice().compareTo(price) >=0){
                return buyOrder;
            }
        }
        throw new OrderNotFoundException("No buy order found");
    }
}
