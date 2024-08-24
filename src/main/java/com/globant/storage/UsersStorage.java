package com.globant.storage;

import com.globant.model.User;

public interface UsersStorage {
    void saveUser(Integer uniqueId, User user);
    User getUserByEmail(String email);
    User getUserById(Integer id);
    Integer generateUniqueId();
}
