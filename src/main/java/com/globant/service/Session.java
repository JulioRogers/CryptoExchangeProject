package com.globant.service;

import com.globant.model.User;

public interface Session {
    String createUser(String name, String email, String password);
    User login(String email, String password);

    }
