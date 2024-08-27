package com.globant.model;

import java.math.BigDecimal;
import java.util.ArrayList;
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
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new NegativeDepositException("Amount must be greater than zero");
        } else{
            this.fiatBalance = this.fiatBalance.add(amount);
        }
    }

    public void deliverFiat(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) > 0){
            if (amount.compareTo(fiatBalance) <= 0) {
                this.fiatBalance = this.fiatBalance.subtract(amount);
            } else {
                throw new InsufficientFundsException("Insufficient fiat balance.");
            }
        } else{
            throw new NegativeDepositException("Amount must be greater than zero");
        }
    }

    public void receiveCrypto(String crypto, BigDecimal amount) {
        if(amount.compareTo(BigDecimal.ZERO) > 0){
            BigDecimal currentBalance = this.cryptoBalances.get(crypto);
            if(currentBalance == null){
                currentBalance = BigDecimal.ZERO;
            }
            BigDecimal balance = currentBalance.add(amount);
            this.cryptoBalances.put(crypto, balance);
        } else {
            throw new NegativeDepositException("Amount must be greater than zero"); //Cambiar nombre de la excepcion
        }
    }
}