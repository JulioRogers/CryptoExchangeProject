package com.globant.model.wallets;

import com.globant.model.currencies.CryptoCurrency;
import com.globant.model.currencies.Currency;

import java.math.BigDecimal;

/**
 * The {@code ExchangeWallet} class is a specialized wallet designed to handle
 * cryptocurrency transactions exclusively. It extends the {@code Wallet} class
 * to leverage the existing code for receiving and delivering currencies, thus
 * avoiding the repetition of business logic.
 *
 * <p>This class ensures that only cryptocurrencies are processed by overriding
 * the {@code deliverCurrency} and {@code receiveCurrency} methods with checks
 * that enforce this rule.</p>
 *
 * <p>The main purpose of this class is to reuse the code from the {@code Wallet}
 * class for handling currency transactions, while applying specific restrictions
 * pertinent to the project requirements.</p>
 */
 public class ExchangeWallet extends Wallet {
    @Override
    public void deliverCurrency(Currency currency, BigDecimal amount) {
        if (!(currency instanceof CryptoCurrency)) {
            throw new UnsupportedOperationException("ExchangeWallet can only deliver CryptoCurrency.");
        }
        super.deliverCurrency(currency, amount);
    }

    @Override
    public void receiveCurrency(Currency currency, BigDecimal amount) {
        if (!(currency instanceof CryptoCurrency)) {
            throw new UnsupportedOperationException("ExchangeWallet can only receive CryptoCurrency.");
        }
        super.receiveCurrency(currency, amount);
    }
}
