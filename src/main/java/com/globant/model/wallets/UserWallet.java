package com.globant.model.wallets;

import com.globant.model.Transaction;
import com.globant.model.currencies.Currency;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The UserWallet class has a frozenBalances map which is used to manage and track the amounts of currency
 * that are temporarily frozen when a user places an order to buy or sell a cryptocurrency. This gives a better
 * visibility and control of the user's available funds while ensuring that committed funds are not accidentally
 * used for other transactions.
 */
public class UserWallet extends Wallet {
    private final List<Transaction> transactions = new ArrayList<>();
    private final Integer userId;
    private final Map<Currency, BigDecimal> frozenBalances;
    public UserWallet(Integer userId) {
        this.userId = userId;
        this.frozenBalances = new HashMap<>();
    }

    public void saveTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

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