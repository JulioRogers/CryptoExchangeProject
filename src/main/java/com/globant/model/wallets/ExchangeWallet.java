package com.globant.model.wallets;

import com.globant.model.currencies.CryptoCurrency;
import com.globant.model.currencies.Currency;

import java.math.BigDecimal;

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
