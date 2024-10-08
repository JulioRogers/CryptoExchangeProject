package com.globant.controller.loggedin;

import com.globant.exceptions.NegativeAmountException;

import java.math.BigDecimal;


public class DepositFiatController extends LoggedInUserController {

    @Override
    public void run() {
        BigDecimal amount = view.getBigDecimalInput("Enter amount: ");
        try{
            BigDecimal balance = exchangeServiceFacade.depositFiat(user, amount);
            view.showSuccessMessage("Deposit successful, your new balance is: " + balance.toString());
        } catch (NegativeAmountException e){
            view.showError(e.getMessage());
        }
    }
}