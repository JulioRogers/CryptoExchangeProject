package com.globant.model;

public class User {
    private final String uniqueID;
    private final String name;
    private String email;
    private String password;

    public User(String uniqueID, String name, String email, String password) {
        this.uniqueID = uniqueID;
        this.name = name;
        this.email = email;
        this.password = password;
    }
}
