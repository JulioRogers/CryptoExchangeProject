package com.globant.controller.loggedin;

public class GetBalancesController extends LoggedInUserController {
    @Override
    public void run() {
        view.showInfo(exchangeServiceFacade.printUserBalance(user));
    }
}