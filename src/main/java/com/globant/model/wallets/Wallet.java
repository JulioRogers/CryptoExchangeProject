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

    /**
     * Transfers a specified amount of the given currency from the user's balance.
     *
     * @param currency The {@code Currency} to be delivered.
     * @param amount The amount of currency to be delivered.
     */
    public void deliverCurrency(Currency currency, BigDecimal amount) {
        subtractToBalance(currency,amount,this.currencyBalances);
    }

    /**
     * Adds a specified amount of the given currency to the user's balance.
     *
     * @param currency The {@code Currency} to be received.
     * @param amount The amount of currency to be received.
     */
    public void receiveCurrency(Currency currency, BigDecimal amount) {
        addToBalance(currency,amount,this.currencyBalances);
    }

    /**
     * Subtracts a specified amount of the given currency from the balance.
     *
     * <p>This method is protected so it can be reused by the {@code UserWallet} subclass when
     * operating on the frozen balance, promoting code reuse.</p>
     *
     * @param currency The {@code Currency} to subtract from the balance.
     * @param amount The amount of currency to be subtracted.
     * @param balance A {@code Map<Currency, BigDecimal>} representing the balance from which the amount will be subtracted.
     *
     * @throws IllegalArgumentException if the amount exceeds the current balance.
     */
    protected void subtractToBalance(Currency currency, BigDecimal amount, Map<Currency, BigDecimal> balance) {
        BigDecimal currentBalance = getBalance(currency, balance);
        fundsValidation(amount, currentBalance);
        BigDecimal newBalance = currentBalance.subtract(amount);
        balance.put(currency, newBalance);
    }

    /**
     * Adds a specified amount of the given currency to the balance.
     *
     * <p>This method is protected so it can be reused by the {@code UserWallet} subclass when
     * operating on the frozen balance, promoting code reuse.</p>
     *
     * @param currency The {@code Currency} to add to the balance.
     * @param amount The amount of currency to be added.
     * @param balance A {@code Map<Currency, BigDecimal>} representing the balance to which the amount will be added.
     */
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