package com.globant.model;

import com.globant.model.currencies.Currency;
import com.globant.model.wallets.UserWallet;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return Objects.equals(uniqueID  ,user.uniqueID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uniqueID);
    }
}