package com.globant.model;

public class User {
    private final Integer uniqueID;
    private final String name;
    private final String email;
    private final String password;
    private final Wallet wallet;

    public User(Integer uniqueID, String name, String email, String password) {
        this.uniqueID = uniqueID;
        this.name = name;
        this.email = email;
        this.password = password;
        this.wallet = new Wallet(uniqueID);
    }

    public Wallet getWallet() {
        return wallet;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
