package com.globant.controller;

import com.globant.exceptions.*;
import com.globant.model.User;
import com.globant.service.Session;
import com.globant.service.loggedInServices.ExchangeServiceFacade;
import com.globant.view.View;

public class SessionController {
    private final Session sessionService;
    private final View view;
    private final ExchangeServiceFacade exchangeServiceFacade;

    public SessionController(View view, Session sessionService) {
        this.view = view;
        this.sessionService = sessionService;
        this.exchangeServiceFacade = new ExchangeServiceFacade();
    }

    public void createUser() {
        String name = view.getStringInput("Enter name: ");
        String email = view.getStringInput("Enter email: ");
        String password = view.getStringInput("Enter password: ");
        try {
            String userCreationMessage = sessionService.createUser(name, email, password);
            view.showSuccessMessage(userCreationMessage);
        } catch (DuplicateEmailException | InvalidEmailFormatException e){
            view.showError(e.getMessage());
        }
    }

    public void login() {
        String email = view.getStringInput("Enter email: ");
        String password = view.getStringInput("Enter password: ");
        try{
            User loggedInUser = sessionService.login(email, password);
            view.showInfo("Login Successful");
            ExchangeController exchangeController = new ExchangeController(exchangeServiceFacade, view, loggedInUser);
            exchangeController.run();
        } catch (LogOutException e){
            view.showSuccessMessage(e.getMessage());
        } catch (InvalidEmailFormatException | IncorrectPasswordException | UnknownAccountException e){
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