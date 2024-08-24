package com.globant.service;

import com.globant.model.User;
import com.globant.storage.UnknownAccountException;
import com.globant.storage.UsersStorage;

public class UsersSystemService {

    private final UsersStorage usersStorage;

    public UsersSystemService(UsersStorage usersStorage) {
        this.usersStorage = usersStorage;
    }

    public Integer createUser(String name, String email, String password) {
        if (usersStorage.getUserByEmail(email) != null) {
            throw new DuplicateEmailException("Email already used");
        }

        Integer userId = usersStorage.generateUniqueId();

        User user = new User(userId, name, email, password);

        usersStorage.saveUser(userId,user);

        return userId;
    }

    public boolean validateUser(String email, String password) {
        User user = usersStorage.getUserByEmail(email);
        if (user != null && user.getPassword().equals(password)) {
            return true;
        } else {
            throw new UnknownAccountException("Invalid email or password.");
        }
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User getUserById(Integer userId) {
        return userRepository.findById(userId);
    }

}