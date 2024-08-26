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
        if (usersStorage.getUserByEmail(email).isPresent()) {
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
        if (!isValidEmail(email)) {
            throw new InvalidEmailFormatException("Invalid email format");
        }

        User user = usersStorage.getUserByEmail(email).orElseThrow(()->new UnknownAccountException("Email not found"));

        return user.getPassword().equals(password);
    }

    public User getUserbyEmail(String email) {
        return usersStorage.getUserByEmail(email).orElseThrow(() -> new UnknownAccountException("Email not found."));
    }
}