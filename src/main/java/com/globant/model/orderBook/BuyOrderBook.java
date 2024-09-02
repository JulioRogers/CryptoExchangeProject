package com.globant.model.orderBook;

import com.globant.model.currencies.CryptoCurrency;
import com.globant.model.orders.BuyOrder;

import java.math.BigDecimal;

public class BuyOrderBook extends OrderBook<BuyOrder> {

    @Override
    protected BuyOrder matchSearch(BigDecimal amount, BigDecimal price, CryptoCurrency crypto, BuyOrder buyOrder) {
        if (buyOrder.getAmount().compareTo(amount) == 0 && buyOrder.getPrice().compareTo(price) >= 0 && buyOrder.getCrypto().getSymbol().equals(crypto.getSymbol())) {
            return buyOrder;
        }
        return null;
    }
}