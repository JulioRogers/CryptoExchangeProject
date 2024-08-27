package com.globant;

import com.globant.controller.SessionController;
import com.globant.service.SessionService;
import com.globant.storage.InMemoryUsersStorage;
import com.globant.view.ConsoleView;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        InMemoryUsersStorage storage = new InMemoryUsersStorage();
        SessionService sessionService = new SessionService(storage);
        ConsoleView consoleView = new ConsoleView();
        SessionController sessionController = new SessionController(consoleView, sessionService);

        sessionController.run();
    }
}