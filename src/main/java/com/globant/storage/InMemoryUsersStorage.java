package com.globant.storage;

import com.globant.model.User;

import java.util.Map;
import java.util.HashMap;
import java.util.Optional;

public class InMemoryUsersStorage implements UsersStorage {

    private final Map<Integer, User> users = new HashMap<>();
    private int counter = 0;

    @Override
    public Integer saveUser(User user) {
        int uniqueId = counter++;
        users.put(uniqueId, user);
        return uniqueId;
    }

    @Override
    public User getUserByEmail(String email) {
        for (User user : users.values()) {
            if (user.getEmail().equals(email)) {
                return user;
            }
        }
        throw new UnknownAccountException("User not found.");
    }

    @Override
    public User getUserById(Integer id) {
        return Optional.ofNullable(users.get(id))
                .orElseThrow(() -> new UnknownAccountException("User with ID " + id + " not found."));

    }
}