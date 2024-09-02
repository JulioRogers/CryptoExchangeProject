package com.globant.model.orderBook;

import com.globant.model.currencies.CryptoCurrency;
import com.globant.model.orders.SellOrder;

import java.math.BigDecimal;

public class SellOrderBook extends OrderBook<SellOrder> {

    @Override
    protected SellOrder matchSearch(BigDecimal amount, BigDecimal price, CryptoCurrency crypto, SellOrder sellOrder) {
        if (sellOrder.getAmount().compareTo(amount) == 0 && sellOrder.getPrice().compareTo(price) <= 0 && sellOrder.getCrypto().getSymbol().equals(crypto.getSymbol())) {
            return sellOrder;
        }
        return null;
    }
}