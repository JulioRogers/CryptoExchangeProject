package com.globant.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class ExchangeService {
    private final Map<String, BigDecimal> cryptoInitialPrices;

    public ExchangeService() {
        this.cryptoInitialPrices = new HashMap<>();
        this.cryptoInitialPrices.put("BTC", new BigDecimal("50000"));
        this.cryptoInitialPrices.put("ETH", new BigDecimal("3000"));
    }
}
