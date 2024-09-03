package com.globant.service;

import com.globant.model.currencies.CryptoCurrency;
import com.globant.model.currencies.Currency;
import com.globant.model.wallets.ExchangeWallet;
import com.globant.service.loggedInServices.FluctuateCrypto;

import java.math.BigDecimal;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


/**
 * The {@code CryptoFluctuator} class is responsible for fluctuating the value of cryptocurrencies
 * periodically. It schedules a task that runs at fixed intervals, updating the value of each cryptocurrency.
 * <p>
 * The fluctuation process uses the {@link FluctuateCrypto#execute(CryptoCurrency)} method to
 * determine the new value for each cryptocurrency.
 * </p>
 * <p>
 * This code is inspired by an example on Stack Overflow for scheduling a task to print "Hello World"
 * every few seconds. Link:
 * <a href="https://stackoverflow.com/questions/12908412/print-hello-world-every-x-seconds">Stack Overflow Link</a>.
 * </p>
 */
public class CryptoFluctuator {
    private final ExchangeWallet exchangeWallet;
    private final ScheduledExecutorService executor;

    public CryptoFluctuator(ExchangeWallet exchangeWallet) {
        this.exchangeWallet = exchangeWallet;
        this.executor = Executors.newScheduledThreadPool(1);
    }

    public void startFluctuation() {
        executor.scheduleAtFixedRate(() -> {
            for (Currency currency : exchangeWallet.getCurrencies().keySet()) {
                if (currency instanceof CryptoCurrency) {
                    CryptoCurrency crypto = (CryptoCurrency) currency;
                    BigDecimal newValue = FluctuateCrypto.execute(crypto);
                    crypto.setPrice(newValue);
                }
            }
        }, 0, 10, TimeUnit.SECONDS); // Fluctuación cada 10 segundos
    }
}