package com.globant.model;

import com.globant.exceptions.InsufficientFundsException;
import com.globant.model.currencies.Currency;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class Wallet {
    private final Integer userId;
    private BigDecimal fiatBalance;
    private final Map<String, BigDecimal> cryptoBalances;
    private final Map<Currency, BigDecimal> currencyBalances;

    public Wallet(Integer userId) {
        this.userId = userId;
        this.fiatBalance = BigDecimal.ZERO;
        this.cryptoBalances = new HashMap<>();
        this.currencyBalances = new HashMap<>();
    }

    public void receiveFiat(BigDecimal amount) {
        this.fiatBalance = this.fiatBalance.add(amount);
    }

    public void deliverFiat(BigDecimal amount) {
        fundsValidation(amount, this.fiatBalance);
        this.fiatBalance = this.fiatBalance.subtract(amount);
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
}