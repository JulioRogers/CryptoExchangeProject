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
        subtractToBalance(currency,amount,this.currencyBalances);
    }

    public void receiveCurrency(Currency currency, BigDecimal amount) {
        addToBalance(currency,amount,this.currencyBalances);
    }

    protected void subtractToBalance(Currency currency, BigDecimal amount, Map<Currency, BigDecimal> balance) {
        BigDecimal currentBalance = getBalance(currency, balance);
        fundsValidation(amount, currentBalance);
        BigDecimal newBalance = currentBalance.subtract(amount);
        balance.put(currency, newBalance);
    }

    protected void addToBalance(Currency currency, BigDecimal amount, Map<Currency, BigDecimal> balance) {
        BigDecimal currentBalance = getBalance(currency, balance);
        BigDecimal newBalance = currentBalance.add(amount);
        balance.put(currency, newBalance);
    }

    public BigDecimal getWalletBalance(Currency currency) {
        return getBalance(currency, this.currencyBalances);
    }

    protected BigDecimal getBalance(Currency currency, Map<Currency, BigDecimal> balance) {
        return balance.getOrDefault(currency, BigDecimal.ZERO);
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