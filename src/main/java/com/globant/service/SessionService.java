package com.globant.service;

import com.globant.exceptions.DuplicateEmailException;
import com.globant.exceptions.IncorrectPasswordException;
import com.globant.exceptions.InvalidEmailFormatException;
import com.globant.model.User;
import com.globant.exceptions.UnknownAccountException;
import com.globant.model.UsersStorage;

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

    public String login(String email, String password) {
        emailFormatValidation(email);
        User user = usersStorage.getUserByEmail(email);
        checkPassword(user, password);
        currentUser = user;
        return "Login successful";
    }

    public String logout(){
        currentUser = null;
        return "Logout successful";
    }

    public User getCurrentUser() {
        return currentUser;
    }

    private void checkEmailDuplication(String email) {
        try {
            usersStorage.getUserByEmail(email);
            throw new DuplicateEmailException("Email already used");
        } catch (UnknownAccountException ignored) {
        }
    }
    private void checkPassword(User user, String password) {
        if(!user.getPassword().equals(password)){
            throw new IncorrectPasswordException("Incorrect Password");
        }
    }

    private void emailFormatValidation(String email) {
        if (!email.matches(EMAIL_PATTERN)) {
            throw new InvalidEmailFormatException("Invalid email format");
        }
    }
}