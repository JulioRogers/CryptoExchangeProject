package com.globant.service;

import com.globant.exceptions.DuplicateEmailException;
import com.globant.exceptions.IncorrectPasswordException;
import com.globant.exceptions.InvalidEmailFormatException;
import com.globant.model.User;
import com.globant.exceptions.UnknownAccountException;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

public class SessionService implements Session {

    private static final String EMAIL_PATTERN = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
    private final Map<Integer, User> users = new HashMap<>();
    private int counter = 0;

    @Override
    public String createUser(String name, String email, String password) {
        emailFormatValidation(email);
        checkEmailDuplication(email);
        Integer userId = generateUniqueId();
        User user = new User(userId, name, email, password);
        users.put(userId, user);
        return MessageFormat.format("This is your User Id:{0}", userId);
    }

    @Override
    public User login(String email, String password) {
        emailFormatValidation(email);
        User user = getUserByEmail(email);
        checkPassword(user, password);
        return user;
    }

    private void checkEmailDuplication(String email) {
        try {
            getUserByEmail(email);
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

    private User getUserByEmail(String email) {
        for (User user : users.values()) {
            if (user.getEmail().equals(email)) {
                return user;
            }
        }
        throw new UnknownAccountException("User not found.");
    }

    private Integer generateUniqueId() {
        counter++;
        return counter;
    }
}