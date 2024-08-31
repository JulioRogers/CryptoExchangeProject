package com.globant.controller.loggedin;

import com.globant.controller.UserController;
import com.globant.model.User;
import com.globant.service.ExchangeService;
import com.globant.service.orders.BuyOrderService;
import com.globant.service.orders.SellOrderService;
import com.globant.view.View;

public abstract class LoggedInUserController implements UserController {
    protected static ExchangeService exchangeService;
    protected static View view;
    protected static User user;
    protected static SellOrderService sellOrderService;
    protected static BuyOrderService buyOrderService;

    public static void setAttributes(ExchangeService exchangeService, View view, User user, SellOrderService sellOrderService, BuyOrderService buyOrderService) {
        LoggedInUserController.exchangeService = exchangeService;
        LoggedInUserController.view = view;
        LoggedInUserController.user = user;
        LoggedInUserController.sellOrderService = sellOrderService;
        LoggedInUserController.buyOrderService = buyOrderService;
    }

    public abstract void run();
}