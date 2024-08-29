package com.globant;

import com.globant.controller.ExchangeController;
import com.globant.controller.SessionController;
import com.globant.service.ExchangeService;
import com.globant.service.SessionService;
import com.globant.storage.InMemoryUsersStorage;
import com.globant.storage.UsersStorage;
import com.globant.view.ConsoleView;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        UsersStorage storage = new InMemoryUsersStorage();
        SessionService sessionService = new SessionService(storage);
        ConsoleView view = new ConsoleView();
        ExchangeService exchangeService = new ExchangeService(sessionService);
        ExchangeController exchangeController = new ExchangeController(exchangeService, view);
        SessionController sessionController = new SessionController(view, sessionService, exchangeController);

        sessionController.run();
    }
}