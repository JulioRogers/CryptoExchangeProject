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
        emailFormatValidation(email);
        checkEmailDuplication(email);
        Integer userId = usersStorage.generateUniqueId();
        User user = new User(userId, name, email, password);
        usersStorage.saveUser(userId,user);
        return MessageFormat.format("This is your User Id:{0}", userId);
    }

    private void checkEmailDuplication(String email) {
        if (usersStorage.getUserByEmail(email).isPresent()) {
            throw new DuplicateEmailException("Email already used");
        }
    }

    public String login(String email, String password) {
        emailFormatValidation(email);
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

    private void emailFormatValidation(String email) {
        if (!email.matches(EMAIL_PATTERN)) {
            throw new InvalidEmailFormatException("Invalid email format");
        }
    }

    public User getCurrentUser() {
        return currentUser;
    }
}