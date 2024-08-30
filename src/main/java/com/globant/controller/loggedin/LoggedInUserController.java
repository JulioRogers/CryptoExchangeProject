package com.globant.controller.loggedin;

import com.globant.controller.UserController;
import com.globant.model.User;
import com.globant.service.ExchangeService;
import com.globant.view.ConsoleView;

public abstract class LoggedInUserController implements UserController {
    protected static ExchangeService exchangeService;
    protected static ConsoleView view;
    protected static User user;

    public static void setAtributes(ExchangeService exchangeService, ConsoleView view, User user) {
        LoggedInUserController.exchangeService = exchangeService;
        LoggedInUserController.view = view;
        LoggedInUserController.user = user;
    }

    public abstract void run();
}