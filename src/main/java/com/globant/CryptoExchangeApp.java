package com.globant;

import com.globant.controller.ExchangeController;
import com.globant.controller.SessionController;
import com.globant.service.ExchangeService;
import com.globant.service.SessionService;
import com.globant.view.ConsoleView;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class CryptoExchangeApp {
    public static void main(String[] args) {
        SessionService sessionService = new SessionService();
        ConsoleView view = new ConsoleView();
        ExchangeService exchangeService = new ExchangeService();
        SessionController sessionController = new SessionController(view, sessionService, exchangeService);

        sessionController.run();
    }
}