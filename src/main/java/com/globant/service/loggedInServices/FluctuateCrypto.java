package com.globant.service.loggedInServices;

import com.globant.model.currencies.CryptoCurrency;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

/**
 * The {@code FluctuateCrypto} class provides a mechanism to simulate the random fluctuation
 * of cryptocurrency prices. It uses a random percentage to adjust the current price of
 * a cryptocurrency.
 *
 * <p>Key points:</p>
 * <ul>
 *   <li>Price fluctuations are randomized within a small range (up to ±10%).</li>
 *   <li>The resulting price is rounded to two decimal places.</li>
 * </ul>
 *
 * <p>This class cannot be instantiated directly due to its private constructor.</p>
 */

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