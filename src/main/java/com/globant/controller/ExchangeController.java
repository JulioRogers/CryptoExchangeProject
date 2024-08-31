package com.globant.controller;

import com.globant.controller.loggedin.*;
import com.globant.model.User;
import com.globant.service.ExchangeService;
import com.globant.service.orders.BuyOrderService;
import com.globant.service.orders.SellOrderService;
import com.globant.view.View;

import java.util.HashMap;
import java.util.Map;

public class ExchangeController implements UserController {
    private final View view;
    private final Map<Integer, UserController> controllers = new HashMap<>();
    public ExchangeController(ExchangeService exchangeService, View view, User user, SellOrderService sellOrderService, BuyOrderService buyOrderService) {
        this.view = view;
        LoggedInUserController.setAttributes(exchangeService,view,user, sellOrderService, buyOrderService);
        controllers.put(1, new DepositFiatController());
        controllers.put(2, new BuyCryptoController());
        controllers.put(3, new GetBalancesController());
        controllers.put(4, new PlaceBuyOrderController());
        controllers.put(5, new PlaceSellOrderController());
        controllers.put(6, new LogOutController());
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
