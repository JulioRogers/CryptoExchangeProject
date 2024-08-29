package com.globant.controller;

import com.globant.model.User;
import com.globant.service.ExchangeService;
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
        exchangeService.deposit(user, amount);
        view.showSuccessMessage("Deposit successful");
        } catch (RuntimeException e){
            view.showError(e.getMessage());
        }
    }

    public void buyCrypto(){
        String cryptoName = view.getCryptoName();
        cryptoName = checkOptionalNames(cryptoName);
        BigDecimal amount = view.getAmountInput();
        try {
            exchangeService.buyCrypto(cryptoName, amount);
            view.showSuccessMessage("Buy Successfully");
        } catch (RuntimeException e){
            view.showError(e.getMessage());
        }
    }

    private static String checkOptionalNames(String cryptoName) {
        if (cryptoName.equals("BITCOIN")){
            cryptoName = "BTC";
        } else if (cryptoName.equals("ETHEREUM") || cryptoName.equals("ETHER")) {
            cryptoName = "ETH";
        }
        return cryptoName;
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
