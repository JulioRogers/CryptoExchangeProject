package com.globant.controller;

import com.globant.controller.loggedin.BuyCryptoController;
import com.globant.controller.loggedin.DepositFiatController;
import com.globant.controller.loggedin.GetBalancesController;
import com.globant.controller.loggedin.LoggedInUserController;
import com.globant.model.User;
import com.globant.service.ExchangeService;
import com.globant.view.ConsoleView;

import java.util.HashMap;
import java.util.Map;

public class ExchangeController implements UserController {
    private final ConsoleView view;
    private final Map<Integer, UserController> controllers = new HashMap<>();
    public ExchangeController(ExchangeService exchangeService, ConsoleView view, User user, ConsoleView view1) {
        this.view = view;
        LoggedInUserController.setAtributes(exchangeService,view,user);
        controllers.put(1, new DepositFiatController());
        controllers.put(2, new BuyCryptoController());
        controllers.put(3, new GetBalancesController());
    }

    public void run(){
        boolean loggedIn = true;
        while(loggedIn){
            int loggedInChoice = view.getLoggedInChoice();
            UserController controller = controllers.get(loggedInChoice);
            if (controller != null){
                controller.run();
                if (loggedInChoice == 4) {
                    loggedIn = false;
                }
            } else {
                view.showError("Invalid choice. Please try again.");
            }
        }
    }
}
