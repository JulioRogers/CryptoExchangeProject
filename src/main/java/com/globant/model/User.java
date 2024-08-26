package com.globant.model;

public class User {
    private final Integer uniqueID;
    private final String name;
    private final String email;
    private final String password;

    public User(Integer uniqueID, String name, String email, String password) {
        this.uniqueID = uniqueID;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
