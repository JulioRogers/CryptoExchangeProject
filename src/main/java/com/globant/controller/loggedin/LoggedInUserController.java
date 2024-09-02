package com.globant.controller.loggedin;

import com.globant.controller.UserController;
import com.globant.model.User;
import com.globant.service.loggedInServices.ExchangeServiceFacade;
import com.globant.view.View;
/**
 * The {@code LoggedInUserController} class manages common dependencies for controllers
 * that handle actions that can be only performed by logged-in users.
 *
 * <p>This class is the base controller for actions that require an active session.</p>
 *
 * <p>It contains static attributes that are shared with its subclasses, including:</p>
 * <ul>
 *   <li>{@code exchangeServiceFacade} - Manages the business logic.</li>
 *   <li>{@code view} - Manages the user interface display.</li>
 *   <li>{@code loggedInUser} - Represents the user currently logged in.</li>
 * </ul>
 */
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