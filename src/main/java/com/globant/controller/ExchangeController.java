package com.globant.controller;

import com.globant.controller.loggedin.*;


import java.util.HashMap;
import java.util.Map;

/**
 * The {@code ExchangeController} class handles operations related to exchanging cryptocurrencies.
 * It acts as a central point for interacting with different services like buying, selling,
 * and managing cryptocurrency assets. This controller uses the Facade design pattern
 * to make it easier to interact with the system's services.
 *
 * This class is used by logged-in users to perform exchange operations.
 */
public class ExchangeController extends LoggedInUserController {
    private final Map<Integer, UserController> controllers = new HashMap<>();
    public ExchangeController() {
        controllers.put(1, new DepositFiatController());
        controllers.put(2, new BuyCryptoController());
        controllers.put(3, new GetBalancesController());
        controllers.put(4, new PlaceBuyOrderController());
        controllers.put(5, new PlaceSellOrderController());
        controllers.put(6, new GetTransactionsController());
        controllers.put(7, new SeeCryptoPriceController());
        controllers.put(8, new LogOutController());
    }

    public void run(){
        while(true){
            int loggedInChoice = view.getLoggedInChoice();
            UserController controller = controllers.get(loggedInChoice);
            if (controller != null){
                controller.run();
            } else {
                view.showError("Invalid choice. Please try again.");
            }
        }
    }
}
