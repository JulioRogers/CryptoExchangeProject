package com.globant.storage;

import com.globant.model.User;

import java.util.Optional;

public interface UsersStorage {
    void saveUser(Integer uniqueId, User user);
    User getUserByEmail(String email);
    User getUserById(Integer id);
    Integer generateUniqueId();
}
