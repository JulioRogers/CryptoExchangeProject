package com.globant.controller;

import com.globant.model.User;
import com.globant.service.ExchangeService;
import com.globant.service.SessionService;
import com.globant.view.ConsoleView;

public class SessionController {
    private final SessionService sessionService;
    private final ConsoleView view;
    private final ExchangeService exchangeService;

    public SessionController(ConsoleView view, SessionService sessionService, ExchangeService exchangeService) {
        this.view = view;
        this.sessionService = sessionService;
        this.exchangeService = exchangeService;
    }

    public void createUser() {
        String name = view.getNameInput();
        String email = view.getEmailInput();
        String password = view.getPasswordInput();
        try {
            String userCreationMessage = sessionService.createUser(name, email, password);
            view.showSuccessMessage(userCreationMessage);
        } catch (RuntimeException e){
            view.showError(e.getMessage());
        }
    }

    public void login() {
        String email = view.getEmailInput();
        String password = view.getPasswordInput();
        try{
            User loggedInUser = sessionService.login(email, password);
            view.showInfo("Login Successful");
            ExchangeController exchangeController = new ExchangeController(exchangeService, view, loggedInUser);
            exchangeController.run();
        } catch (RuntimeException e){
            view.showError(e.getMessage());
        }
    }

    public void run(){
        while (true) {
            int choice = view.getUserChoice();
            switch (choice) {
                case 1:
                    createUser();
                    break;
                case 2:
                    login();
                    break;
                case 3:
                    System.exit(0);
                default:
                    view.showError("Invalid choice. Try again.");
            }
        }
    }
}