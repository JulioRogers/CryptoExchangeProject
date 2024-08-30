package com.globant.model.wallets;

import com.globant.exceptions.InsufficientFundsException;
import com.globant.model.currencies.Currency;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public abstract class Wallet {
    private final Map<Currency, BigDecimal> currencyBalances;

    public Wallet() {
        this.currencyBalances = new HashMap<>();
    }

    public void deliverCurrency(Currency currency, BigDecimal amount) {
        BigDecimal currentBalance = getBalance(currency);
        fundsValidation(amount, currentBalance);
        BigDecimal newBalance = currentBalance.subtract(amount);
        this.currencyBalances.put(currency, newBalance);
    }

    public void receiveCurrency(Currency currency, BigDecimal amount) {
        BigDecimal currentBalance = getBalance(currency);
        BigDecimal newBalance = currentBalance.add(amount);
        this.currencyBalances.put(currency, newBalance);
    }

    public BigDecimal getBalance(Currency currency) {
        return this.currencyBalances.getOrDefault(currency, BigDecimal.ZERO);
    }

    private void fundsValidation(BigDecimal amount, BigDecimal funds) {
        if (amount.compareTo(funds) > 0) {
            throw new InsufficientFundsException("Insufficient fiat balance.");
        }
    }

    public Map<Currency, BigDecimal> getCurrencies() {
        return new HashMap<>(this.currencyBalances);
    }
}