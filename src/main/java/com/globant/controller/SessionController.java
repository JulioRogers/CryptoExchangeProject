package com.globant.controller;

import com.globant.service.DuplicateEmailException;
import com.globant.service.InvalidEmailFormatException;
import com.globant.service.SessionService;
import com.globant.storage.UnknownAccountException;
import com.globant.view.ConsoleView;

public class SessionController {
    private final SessionService sessionService;
    private final ConsoleView view;
    private final ExchangeController exchangeController;

    public SessionController(ConsoleView view, SessionService sessionService, ExchangeController exchangeController) {
        this.view = view;
        this.sessionService = sessionService;
        this.exchangeController = exchangeController;
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

    public boolean login() {
        String email = view.getEmailInput();
        String password = view.getPasswordInput();
        try{
            view.showInfo(sessionService.login(email, password));
            return true;
        } catch (RuntimeException e){
            view.showError(e.getMessage());
            return false;
        }
    }

    public void logout() {
        view.showInfo(sessionService.logout());
    }

    public void run(){
        while (true) {
            int choice = view.getUserChoice();
            switch (choice) {
                case 1:
                    createUser();
                    break;
                case 2:
                    if(login()){
                        exchangeController.run(sessionService.getCurrentUser());
                    };
                    break;
                case 3:
                    System.exit(0);
                default:
                    view.showError("Invalid choice. Try again.");
            }
        }
    }
}