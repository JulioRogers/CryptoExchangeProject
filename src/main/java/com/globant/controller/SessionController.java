package com.globant.controller;

import com.globant.service.DuplicateEmailException;
import com.globant.service.InvalidEmailFormatException;
import com.globant.service.SessionService;
import com.globant.storage.UnknownAccountException;
import com.globant.view.ConsoleView;

public class SessionController {
    private final SessionService sessionService;
    private final ConsoleView view;

    public SessionController(ConsoleView view, SessionService sessionService) {
        this.view = view;
        this.sessionService = sessionService;
    }

    public void createUser() {
        String name = view.getNameInput();
        String email = view.getEmailInput();
        String password = view.getPasswordInput();
        try {
            String userCreationMessage = sessionService.createUser(name, email, password);
            view.showSuccessMessage(userCreationMessage);
        } catch (InvalidEmailFormatException | DuplicateEmailException e){
            view.showError(e.getMessage());
        }
    }

    public void login() {
        String email = view.getEmailInput();
        String password = view.getPasswordInput();
        try{
            view.showInfo(sessionService.login(email, password));
        } catch (UnknownAccountException e){
            view.showError(e.getMessage());
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