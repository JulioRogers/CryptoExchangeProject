package com.globant.controller;

import com.globant.exceptions.LogOutException;

public class LogOutController implements UserController {
    @Override
    public void run() {
        throw new LogOutException("Log Out successfully");
    }
}
