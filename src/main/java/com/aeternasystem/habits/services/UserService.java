package com.aeternasystem.habits.services;

import com.aeternasystem.habits.persistence.model.User;
import com.aeternasystem.habits.util.enums.RoleName;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User save(User user);
    User create(User user);
    User update(User user);
    User register(User user, RoleName roleName);
    void setPasswordIfUpdated(User user, String newPassword);
    void setPassword(User user, String newPassword);
    User register(User user);
    User register(String email, String password, String name, RoleName roleName);
    User register(String chatId, String name);
    User processRegistration(User registeredUser, RoleName roleName);
    Optional<User> findById(long id);
    User getById(long id);
    Optional<User> findByEmail(String email);
    User getByEmail(String email);
    Optional<User> findByChatId(String chatId);
    User getByChatId(String chatId);
    Optional<User> findByUsername(String username);
    Optional<User> findByUsername(String email, String chatId);
    User getByUsername(String username);
    List<User> findAll();
    void delete(User user);
    void delete(long id);
}