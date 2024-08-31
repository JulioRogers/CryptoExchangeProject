package com.globant;

import com.globant.controller.SessionController;
import com.globant.service.SessionService;
import com.globant.view.ConsoleView;
import com.globant.view.View;

public class CryptoExchangeApp {
    public static void main(String[] args) {
        SessionService sessionService = new SessionService();
        View view = new ConsoleView();
        SessionController sessionController = new SessionController(view, sessionService);

        sessionController.run();
        view.close();
    }
}