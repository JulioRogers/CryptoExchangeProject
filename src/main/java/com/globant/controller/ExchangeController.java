package com.globant.controller;

import com.globant.model.InsufficientFundsException;
import com.globant.model.NegativeAmountException;
import com.globant.model.User;
import com.globant.service.ExchangeService;
import com.globant.service.InvalidCryptoException;
import com.globant.view.ConsoleView;

import java.math.BigDecimal;

public class ExchangeController {
    private final ExchangeService exchangeService;
    private final ConsoleView view;

    public ExchangeController(ExchangeService exchangeService, ConsoleView view) {
        this.exchangeService = exchangeService;
        this.view = view;
    }

    public void depositFiat(User user){
        BigDecimal amount = view.getAmountInput();
        try{
        user.getWallet().depositFiat(amount);
        view.showSuccessMessage("Deposit successful");
        } catch (NegativeAmountException e){
            view.showError(e.getMessage());
        }
    }

    public void buyCrypto(){
        String cryptoName = view.getCryptoName();
        if (cryptoName.equals("BITCOIN")){
            cryptoName = "BTC";
        } else if (cryptoName.equals("ETHEREUM")) {
            cryptoName = "ETH";
        }
        BigDecimal amount = view.getAmountInput();

        try {
            exchangeService.buyCrypto(cryptoName, amount);
            view.showSuccessMessage("Buy Successfully");
        } catch (RuntimeException e){
            view.showError(e.getMessage());
        }
    }

    public void run(User user){
        boolean loggedIn = true;
        while(loggedIn){
            int loggedInChoice = view.getLoggedInChoice();
            switch (loggedInChoice) {
                case 1:
                    depositFiat(user);
                    break;
                case 2:
                    buyCrypto();
                    break;
                case 3:
                    exchangeService.logOut();
                    loggedIn = false;
                    break;
                default:
                    view.showError("Invalid choice. Please try again.");
            }
        }
    }
}
