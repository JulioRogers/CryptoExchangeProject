package com.globant.controller.loggedin;

import com.globant.controller.UserController;
import com.globant.exceptions.LogOutException;

public class LogOutController implements UserController {
    @Override
    public void run() {
        throw new LogOutException("Log Out successfully");
    }
}
