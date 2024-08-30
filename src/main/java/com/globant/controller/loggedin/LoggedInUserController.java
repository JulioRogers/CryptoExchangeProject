package com.globant.controller.loggedin;

import com.globant.controller.UserController;
import com.globant.model.User;
import com.globant.service.ExchangeService;
import com.globant.view.View;

public abstract class LoggedInUserController implements UserController {
    protected static ExchangeService exchangeService;
    protected static View view;
    protected static User user;

    public static void setAttributes(ExchangeService exchangeService, View view, User user) {
        LoggedInUserController.exchangeService = exchangeService;
        LoggedInUserController.view = view;
        LoggedInUserController.user = user;
    }

    public abstract void run();
}