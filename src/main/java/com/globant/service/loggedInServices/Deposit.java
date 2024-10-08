package com.globant.service.loggedInServices;

import com.globant.exceptions.NegativeAmountException;
import com.globant.model.User;
import com.globant.model.currencies.FiatCurrency;

import java.math.BigDecimal;

public class Deposit {

    private Deposit(){}

    public static BigDecimal execute(User user, FiatCurrency fiatCurrency, BigDecimal amount) {
        amountValidation(amount);
        user.getWallet().receiveCurrency(fiatCurrency, amount);
        return user.getWallet().getWalletBalance(fiatCurrency);
    }


    private static void amountValidation(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new NegativeAmountException();
        }
    }

}