package com.globant.controller.loggedin;
import com.globant.exceptions.InsufficientFundsException;
import com.globant.exceptions.InvalidCryptoException;
import com.globant.exceptions.NegativeAmountException;

import java.math.BigDecimal;

public class PlaceSellOrderController extends LoggedInUserController{

    @Override
    public void run() {
        String cryptoString = view.getStringInput("Enter crypto name (BTC or ETH): ");
        BigDecimal amount = view.getBigDecimalInput("Enter amount: ");
        BigDecimal price = view.getBigDecimalInput("Enter price: ");
        try {
            String message = exchangeService.placeSellOrder(cryptoString, amount, price, user);
            view.showSuccessMessage(message);
        } catch (InvalidCryptoException | InsufficientFundsException | NegativeAmountException e) {
            view.showError(e.getMessage());
        }
    }
}
