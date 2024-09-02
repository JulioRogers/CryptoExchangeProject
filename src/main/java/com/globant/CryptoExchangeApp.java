package com.globant;

import com.globant.controller.SessionController;
import com.globant.controller.loggedin.LoggedInUserController;
import com.globant.service.Session;
import com.globant.service.SessionService;
import com.globant.service.loggedInServices.ExchangeServiceFacade;
import com.globant.view.ConsoleView;
import com.globant.view.View;

public class CryptoExchangeApp {
    public static void main(String[] args) {
        Session sessionService = new SessionService();
        View view = new ConsoleView();
        ExchangeServiceFacade exchangeServiceFacade = new ExchangeServiceFacade();
        LoggedInUserController.initialize(exchangeServiceFacade,view);
        SessionController sessionController = new SessionController(view, sessionService);

        sessionController.run();
        view.close();
    }
}