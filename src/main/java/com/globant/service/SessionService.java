package com.globant.service;

import com.globant.model.User;
import com.globant.storage.UnknownAccountException;
import com.globant.storage.UsersStorage;

import java.text.MessageFormat;

public class SessionService {

    private final UsersStorage usersStorage;
    private User currentUser;
    private static final String EMAIL_PATTERN = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";


    public SessionService(UsersStorage usersStorage) {
        this.usersStorage = usersStorage;
    }

    public String createUser(String name, String email, String password) {
        if (!isValidEmail(email)) {
            throw new InvalidEmailFormatException("Invalid email format");
        }
        if (usersStorage.getUserByEmail(email).isPresent()) {
            throw new DuplicateEmailException("Email already used");
        }

        Integer userId = usersStorage.generateUniqueId();

        User user = new User(userId, name, email, password);

        usersStorage.saveUser(userId,user);

        return MessageFormat.format("This is your User Id:{0}", userId);
    }

    public String login(String email, String password) {
        User user = usersStorage.getUserByEmail(email).orElseThrow(()->new UnknownAccountException("Email not found"));

        if(user.getPassword().equals(password)) {
            currentUser = user;
            return "Login successful";
        } else {
            return "Incorrect Password";
        }
    }

    public String logout(){
        currentUser = null;
        return "Logout successful";
    }


    private boolean isValidEmail(String email) {
        return email.matches(EMAIL_PATTERN);
    }
}