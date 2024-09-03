package com.globant.controller.loggedin;

import com.globant.exceptions.LogOutException;

public class LogOutController extends LoggedInUserController {
    @Override
    public void run() {
        logOutUser();
        throw new LogOutException("Log Out successfully");
    }
}
