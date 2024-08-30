package com.globant.model;

import com.globant.model.wallets.UserWallet;

public class User {
    private final Integer uniqueID;
    private final String name;
    private final String email;
    private final String password;
    private final UserWallet userWallet;

    public User(Integer uniqueID, String name, String email, String password) {
        this.uniqueID = uniqueID;
        this.name = name;
        this.email = email;
        this.password = password;
        this.userWallet = new UserWallet(uniqueID);
    }

    public UserWallet getWallet() {
        return userWallet;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
