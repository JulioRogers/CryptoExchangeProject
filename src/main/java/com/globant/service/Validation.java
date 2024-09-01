package com.globant.service;

import com.globant.exceptions.InsufficientFundsException;
import com.globant.exceptions.NegativeAmountException;
import com.globant.model.currencies.Currency;
import com.globant.model.currencies.FiatCurrency;
import com.globant.model.wallets.Wallet;

import java.math.BigDecimal;

public class Validation {

    private Validation() {}

    public static void amountValidation(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new NegativeAmountException();
        }
    }

    public static void checkCurrencyFunds(BigDecimal amount, Currency currency, Wallet wallet) {
        if (wallet.getWalletBalance(currency).compareTo(amount) < 0) {
            if (currency instanceof FiatCurrency) {
                throw new InsufficientFundsException("Not enough fiat in your balance.");
            } else {
                throw new InsufficientFundsException("Not enough cryptos to sell.");
            }
        }
    }
}
