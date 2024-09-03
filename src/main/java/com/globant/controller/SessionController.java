package com.globant.controller;

import com.globant.controller.loggedin.LoggedInUserController;
import com.globant.exceptions.*;
import com.globant.model.User;
import com.globant.service.Session;
import com.globant.service.loggedInServices.ExchangeServiceFacade;
import com.globant.view.View;

/**
 * The {@code SessionController} class manages functionalities for no logged-in users.
 * <b>Functionalities:</b> create user, log in and close app functionalities.
 */
public class SessionController {
    private final Session sessionService;
    private final View view;
    private final ExchangeController exchangeController = new ExchangeController();

    public SessionController(View view, Session sessionService) {
        this.view = view;
        this.sessionService = sessionService;

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
    /**
     * This {@code login} method attempts to log in using the {@code sessionService}.
     *  If the login is successful, a new {@code ExchangeController} is created for the logged-in user,
     *  and the logged-in process is initiated. By this way, system allows multiple users by creating
     *  a new exchange controller instance for each user.
     *
     * <p>Exceptions handled:</p>
     * <ul>
     *   <li>{@code LogOutException} - Thrown when the user decides to log out during the login process.</li>
     *   <li>{@code InvalidEmailFormatException} - Thrown when the email format is incorrect.</li>
     *   <li>{@code IncorrectPasswordException} - Thrown when the password does not match the account.</li>
     *   <li>{@code UnknownAccountException} - Thrown when the account does not exist.</li>
     * </ul>
     */
    public void login() {
        String email = view.getStringInput("Enter email: ");
        String password = view.getStringInput("Enter password: ");
        try{
            User loggedInUser = sessionService.login(email, password);
            view.showInfo("Login Successful");
            LoggedInUserController.logInUser(loggedInUser);
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