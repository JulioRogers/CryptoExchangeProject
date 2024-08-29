package com.globant.model;

import com.globant.exceptions.InsufficientFundsException;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class Wallet {
    private final Integer userId;
    private BigDecimal fiatBalance;
    private final Map<String, BigDecimal> cryptoBalances;

    public Wallet(Integer userId) {
        this.userId = userId;
        this.fiatBalance = BigDecimal.ZERO;
        this.cryptoBalances = new HashMap<>();
    }

    public void receiveFiat(BigDecimal amount) {
        this.fiatBalance = this.fiatBalance.add(amount);
    }

    public void deliverFiat(BigDecimal amount) {
        fundsValidation(amount, this.fiatBalance);
        this.fiatBalance = this.fiatBalance.subtract(amount);
    }

    public void receiveCrypto(String crypto, BigDecimal amount) {
        BigDecimal currentBalance = this.cryptoBalances.getOrDefault(crypto, BigDecimal.ZERO);
        BigDecimal newBalance = currentBalance.add(amount);
        this.cryptoBalances.put(crypto, newBalance);
    }


    private void fundsValidation(BigDecimal amount, BigDecimal funds) {
        if (amount.compareTo(funds) > 0) {
            throw new InsufficientFundsException("Insufficient fiat balance.");
        }
    }
}