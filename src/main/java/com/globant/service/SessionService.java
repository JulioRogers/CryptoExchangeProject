package com.globant.service;

import com.globant.model.User;

public class SessionService {

    private User user=null;

    public void login(User user){
        this.user = user;
    }

    public void logout(){
        this.user = null;
    }
}
