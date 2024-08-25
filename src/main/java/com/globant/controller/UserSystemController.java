package com.globant.controller;

import com.globant.service.DuplicateEmailException;
import com.globant.service.InvalidEmailFormatException;
import com.globant.service.UsersSystemService;
import com.globant.storage.UnknownAccountException;
import com.globant.view.ConsoleView;

public class UserSystemController {
    private final UsersSystemService usersSystemService;
    private final ConsoleView view;

    public UserSystemController(ConsoleView view, UsersSystemService usersSystemService) {
        this.view = view;
        this.usersSystemService = usersSystemService;
    }

    public void createUser() {

        String name = view.getNameInput();
        String email = view.getEmailInput();
        String password = view.getPasswordInput();
        try {
            String userCreationMessage = usersSystemService.createUser(name, email, password);
            view.showSuccessMessage(userCreationMessage);

        } catch (InvalidEmailFormatException | DuplicateEmailException e){
            view.showError(e.getMessage());
        }
    }


    public void login() {
        String email = view.getEmailInput();
        String password = view.getPasswordInput();
        try{
            boolean isValid = usersSystemService.validateUser(email, password);
            if(isValid){
                view.showSuccessMessage("User successfully logged in");
            } else{
                view.showError("Incorrect Password");
            }
        } catch (InvalidEmailFormatException | UnknownAccountException e){
            view.showError(e.getMessage());
        }
    }
}
