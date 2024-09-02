package com.globant.controller.loggedin;


public class GetTransactionsController extends LoggedInUserController{

    @Override
    public void run() {
        view.showInfo(exchangeServiceFacade.getTransactions(user));
    }
}
