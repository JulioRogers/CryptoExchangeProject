package com.globant.model.currencies;

import java.math.BigDecimal;

public abstract class Currency {
    private final String name;
    private final String symbol;
    private BigDecimal price;

    public Currency(String name, String symbol, BigDecimal initialPrice) {
        this.name = name;
        this.symbol = symbol;
        this.price = initialPrice;
    }

    public String getName() {
        return name;
    }

    public String getSymbol() {
        return symbol;
    }
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal newPrice){
        this.price = newPrice;
    }
}