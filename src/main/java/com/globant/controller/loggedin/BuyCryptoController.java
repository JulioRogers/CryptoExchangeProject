package com.globant.controller.loggedin;

import com.globant.exceptions.InsufficientFundsException;
import com.globant.exceptions.InvalidCryptoException;
import com.globant.exceptions.NegativeAmountException;

import java.math.BigDecimal;

public class BuyCryptoController extends LoggedInUserController {

    @Override
    public void run() {
        String cryptoName = view.getStringInput("Enter crypto name (BTC or ETH): ");
        BigDecimal amount = view.getBigDecimalInput("Enter amount: ");
        try {
            exchangeService.buyCrypto(cryptoName, amount, user);
            view.showSuccessMessage("Buy Successfully");
        } catch (NegativeAmountException | InsufficientFundsException | InvalidCryptoException e){
            view.showError(e.getMessage());
        }
    }
}
