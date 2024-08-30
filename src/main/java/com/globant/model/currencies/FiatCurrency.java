package com.globant.model.currencies;

import java.math.BigDecimal;

public class FiatCurrency extends Currency {

    public FiatCurrency(String name, String symbol, BigDecimal initialPrice) {
        super(name,symbol,initialPrice);
    }

    @Override
    public void setPrice(BigDecimal price) {
        throw new UnsupportedOperationException("For now, Fiat cannot be changed.");
    }
}
