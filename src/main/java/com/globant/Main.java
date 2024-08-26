package com.globant;

import com.globant.controller.UserSystemController;
import com.globant.service.UsersSystemService;
import com.globant.storage.InMemoryUsersStorage;
import com.globant.view.ConsoleView;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        InMemoryUsersStorage storage = new InMemoryUsersStorage();
        UsersSystemService usersSystemService = new UsersSystemService(storage);
        ConsoleView consoleView = new ConsoleView();
        UserSystemController userSystemController = new UserSystemController(consoleView, usersSystemService);

        while (true) {
            int choice = consoleView.getUserChoice();
            switch (choice) {
                case 1:
                    userSystemController.createUser();
                    break;
                case 2:
                    userSystemController.login();
                    break;
                case 3:
                    userSystemController.logout();
                    break;
                case 4:
                    System.exit(0);
                default:
                    consoleView.showError("Invalid choice. Try again.");
            }
        }
    }
}