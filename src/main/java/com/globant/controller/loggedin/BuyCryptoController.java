package com.globant.controller.loggedin;

import java.math.BigDecimal;

public class BuyCryptoController extends LoggedInUserController {

    @Override
    public void run() {
        String cryptoName = view.getCryptoName();
        BigDecimal amount = view.getAmountInput();
        try {
            exchangeService.buyCrypto(cryptoName, amount);
            view.showSuccessMessage("Buy Successfully");
        } catch (RuntimeException e){
            view.showError(e.getMessage());
        }
    }
}
