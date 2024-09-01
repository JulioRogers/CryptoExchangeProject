package com.globant.service;

import com.globant.model.currencies.CryptoCurrency;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

public class FluctuateCrypto {
    CryptoCurrency crypto;

    private FluctuateCrypto(){}

    public static BigDecimal execute(CryptoCurrency crypto){
        return randomFluctuation(crypto);
    }

    private static BigDecimal randomFluctuation(CryptoCurrency crypto){
        BigDecimal price = crypto.getPrice();
        Random rand = new Random();
        BigDecimal fluctuationPercentage = BigDecimal.valueOf((2 * rand.nextDouble() - 1) * 0.1);
        return price.add(price.multiply(fluctuationPercentage)).setScale(2, RoundingMode.HALF_EVEN);
    }
}