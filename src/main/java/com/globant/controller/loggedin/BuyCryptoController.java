package com.globant.controller.loggedin;

import java.math.BigDecimal;

public class BuyCryptoController extends LoggedInUserController {

    @Override
    public void run() {
        String cryptoName = view.getStringInput("Enter crypto name (BTC or ETH): ");
        BigDecimal amount = view.getBigDecimalInput();
        try {
            exchangeService.buyCrypto(cryptoName, amount, user);
            view.showSuccessMessage("Buy Successfully");
        } catch (RuntimeException e){
            view.showError(e.getMessage());
        }
    }
}
