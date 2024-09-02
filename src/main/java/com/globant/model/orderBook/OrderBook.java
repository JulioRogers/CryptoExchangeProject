package com.globant.model.orderBook;

import com.globant.exceptions.OrderNotFoundException;
import com.globant.model.currencies.CryptoCurrency;
import com.globant.model.orders.Order;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.Queue;

public abstract class OrderBook<T extends Order> {
    protected final Queue<T> orders = new LinkedList<>();

    public void addOrder(T order) {
        orders.offer(order);
    }

    /**
     * Retrieves and removes an order from the queue based on specific criteria.
     *
     * @param crypto The cryptocurrency of the order.
     * @param amount The amount of the order.
     * @param price The price of the order.
     * @return The order that matches the criteria.
     * @throws OrderNotFoundException if no matching order is found.
     */
    public T getOrder(BigDecimal amount, BigDecimal price, CryptoCurrency crypto) {
        for (T order : orders) {
            T matchingOrder = matchSearch(amount, price, crypto, order);
            if (matchingOrder != null) {
                orders.remove(order);
                return matchingOrder;
            }
        }
        throw new OrderNotFoundException("No matching order found");
    }

    /**
     * Abstract method that defines the matching criteria for orders.
     *
     * @param amount The amount of the order.
     * @param price The price of the order.
     * @param crypto The cryptocurrency of the order.
     * @param order The order being evaluated.
     * @return The matching order if criteria are met, otherwise null.
     */
    protected abstract T matchSearch(BigDecimal amount, BigDecimal price, CryptoCurrency crypto, T order);
}