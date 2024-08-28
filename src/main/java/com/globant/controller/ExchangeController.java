package com.globant.controller;

import com.globant.model.NegativeAmountException;
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
        user.getWallet().depositFiat(amount);
        view.showSuccessMessage("Deposit successful");
        } catch (NegativeAmountException e){
            view.showError(e.getMessage());
        }
    }
}
