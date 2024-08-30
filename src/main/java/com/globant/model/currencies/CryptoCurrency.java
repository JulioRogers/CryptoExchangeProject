package com.globant.model.currencies;

import java.math.BigDecimal;

public class CryptoCurrency extends Currency {
    public CryptoCurrency(String name, String symbol, BigDecimal initialPrice) {
        super(name, symbol, initialPrice);
    }
}