package com.globant.controller.loggedin;

import com.globant.controller.UserController;
import com.globant.model.User;
import com.globant.service.loggedInServices.ExchangeServiceFacade;
import com.globant.view.View;

public abstract class LoggedInUserController implements UserController {
    protected static ExchangeServiceFacade exchangeServiceFacade;
    protected static View view;
    protected static User user;

    public static void initialize(ExchangeServiceFacade exchangeServiceFacade, View view, User user) {
        if (LoggedInUserController.exchangeServiceFacade != null ||
                LoggedInUserController.view != null ||
                LoggedInUserController.user != null) {
            throw new RuntimeException("Attributes already set and cannot be reconfigured.");
        }
        LoggedInUserController.exchangeServiceFacade = exchangeServiceFacade;
        LoggedInUserController.view = view;
        LoggedInUserController.user = user;
    }

    public abstract void run();
}