package com.globant;

import com.globant.controller.SessionController;
import com.globant.service.SessionService;
import com.globant.view.ConsoleView;
import com.globant.view.View;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class CryptoExchangeApp {
    public static void main(String[] args) {
        SessionService sessionService = new SessionService();
        View view = new ConsoleView();
        SessionController sessionController = new SessionController(view, sessionService);

        sessionController.run();
    }
}