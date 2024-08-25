package com.globant.service;

import com.globant.model.User;
import com.globant.storage.UnknownAccountException;
import com.globant.storage.UsersStorage;

import java.text.MessageFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UsersSystemService {

    private final UsersStorage usersStorage;
    private static final String EMAIL_PATTERN = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
    private final Pattern pattern = Pattern.compile(EMAIL_PATTERN);


    public UsersSystemService(UsersStorage usersStorage) {
        this.usersStorage = usersStorage;
    }

    public String createUser(String name, String email, String password) {
        if (!isValidEmail(email)) {
            throw new InvalidEmailFormatException("Invalid email format");
        }
        if (usersStorage.getUserByEmail(email) != null) {
            throw new DuplicateEmailException("Email already used");
        }

        Integer userId = usersStorage.generateUniqueId();

        User user = new User(userId, name, email, password);

        usersStorage.saveUser(userId,user);

        return MessageFormat.format("This is your User Id:{0}", userId);
    }

    private boolean isValidEmail(String email) {
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public boolean validateUser(String email, String password) {
        User user = usersStorage.getUserByEmail(email);
        if (user != null && user.getPassword().equals(password)) {
            return true;
        } else {
            throw new UnknownAccountException("Invalid email or password.");
        }
    }
}