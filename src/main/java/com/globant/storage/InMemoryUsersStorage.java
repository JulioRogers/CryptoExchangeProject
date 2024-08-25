package com.globant.storage;

import com.globant.model.User;

import java.util.Map;
import java.util.HashMap;
import java.util.Optional;

public class InMemoryUsersStorage implements UsersStorage {

    private final Map<Integer, User> users = new HashMap<>();
    private int counter = 0;

    @Override
    public void saveUser(Integer uniqueId, User user) {
        users.put(uniqueId, user);
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        for (User user : users.values()) {
            if (user.getEmail().equals(email)) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }

    @Override
    public User getUserById(Integer id) {
        return Optional.ofNullable(users.get(id))
                .orElseThrow(() -> new UnknownAccountException("User not found."));

    }

    @Override
    public Integer generateUniqueId() {
        counter++;
        return counter;
    }
}