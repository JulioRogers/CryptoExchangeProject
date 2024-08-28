package com.globant.model;

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

    public void depositFiat(BigDecimal amount) {
        amountValidation(amount);
        this.fiatBalance = this.fiatBalance.add(amount);
    }

    public void deliverFiat(BigDecimal amount) {
        amountValidation(amount);
        if (amount.compareTo(fiatBalance) <= 0) {
                this.fiatBalance = this.fiatBalance.subtract(amount);
            } else {
                throw new InsufficientFundsException("Insufficient fiat balance.");
            }
    }

    public void receiveCrypto(String crypto, BigDecimal amount) {
        amountValidation(amount);
        BigDecimal currentBalance = this.cryptoBalances.get(crypto);
        if(currentBalance == null){
            currentBalance = BigDecimal.ZERO;
        }
        BigDecimal balance = currentBalance.add(amount);
        this.cryptoBalances.put(crypto, balance);
    }

    public void amountValidation(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new NegativeAmountException();
        }
    }
}