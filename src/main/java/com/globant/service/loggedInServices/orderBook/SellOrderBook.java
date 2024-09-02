package com.globant.service.loggedInServices.orderBook;

import com.globant.exceptions.OrderNotFoundException;
import com.globant.model.currencies.CryptoCurrency;
import com.globant.model.orders.Order;
import com.globant.model.orders.SellOrder;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.Queue;

public class SellOrderBook extends OrderBook<SellOrder> {

    @Override
    protected SellOrder matchSearch(BigDecimal amount, BigDecimal price, CryptoCurrency crypto, SellOrder sellOrder) {
        if (sellOrder.getAmount().compareTo(amount) == 0 && sellOrder.getPrice().compareTo(price) <= 0 && sellOrder.getCrypto().getSymbol().equals(crypto.getSymbol())) {
            return sellOrder;
        }
        return null;
    }
}