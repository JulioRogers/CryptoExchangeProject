package com.globant.model.wallets;

import com.globant.model.Transaction;
import com.globant.model.currencies.Currency;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserWallet extends Wallet {
    private final List<Transaction> transactions = new ArrayList<>();
    private final Integer userId;
    private final Map<Currency, BigDecimal> frozenBalances;
    public UserWallet(Integer userId) {
        this.userId = userId;
        this.frozenBalances = new HashMap<>();
    }

    public void saveTransaction(){}

    public Map<Currency, BigDecimal> getFrozenCurrencies() {
        return new HashMap<>(this.frozenBalances);
    }

    public void frozeCurrency(Currency currency, BigDecimal amount) {
        addToBalance(currency, amount, this.frozenBalances);
    }

    public void unfrozeCurrency(Currency currency, BigDecimal amount) {
        subtractToBalance(currency, amount, this.frozenBalances);
    }
}