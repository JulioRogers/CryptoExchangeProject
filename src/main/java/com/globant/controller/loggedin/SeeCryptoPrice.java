package com.globant.controller.loggedin;

import com.globant.exceptions.InvalidCryptoException;

import java.math.BigDecimal;

public class SeeCryptoPrice extends LoggedInUserController{

    @Override
    public void run() {
        try {
            String cryptoString = view.getStringInput("Enter crypto name (BTC or ETH): ");
            BigDecimal value = exchangeService.getCryptoValue(cryptoString);
            view.showInfo(value.toString());
        } catch (InvalidCryptoException e) {
            view.showError(e.getMessage());
        }
    }
}