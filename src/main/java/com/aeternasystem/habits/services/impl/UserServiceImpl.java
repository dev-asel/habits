package com.aeternasystem.habits.services.impl;

import com.aeternasystem.habits.exception.UserAlreadyExistException;
import com.aeternasystem.habits.exception.UserNotFoundException;
import com.aeternasystem.habits.persistence.model.User;
import com.aeternasystem.habits.persistence.repositories.UserRepository;
import com.aeternasystem.habits.services.RoleManagementService;
import com.aeternasystem.habits.services.UserService;
import com.aeternasystem.habits.services.cache.UserCache;
import com.aeternasystem.habits.util.enums.RoleName;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
@Service
@Transactional
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;
    private final RoleManagementService roleManagementService;
    private final UserCache userCache;
    private final PasswordEncoder passwordEncoder;


    public UserServiceImpl(UserRepository userRepository, RoleManagementService roleManagementService, UserCache userCache, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleManagementService = roleManagementService;
        this.userCache = userCache;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User save(User user) {
        User savedUser = userRepository.save(user);
        userCache.put(savedUser);
        return savedUser;
    }

    @Override
    public User create(User user) {
        String username = user.getUsername();
        if (findByUsername(username).isPresent()) {
            throw new UserAlreadyExistException(String.format("User with username %s already exists", username));
        }
        setPassword(user, user.getPassword());

        User savedUser = save(user);
        logger.info("User with ID '{}' created", savedUser.getId());
        return savedUser;
    }

    @Override
    public User update(User user) {
        User existingUser = getById(user.getId());

        existingUser.setName(user.getName());
        existingUser.setChatId(user.getChatId());
        existingUser.setEmail(user.getEmail());
        setPasswordIfUpdated(existingUser, user.getPassword());
        roleManagementService.setRoles(existingUser, user.getRoles());

        User savedUser = save(existingUser);
        logger.info("User with ID '{}' updated", savedUser.getId());
        return savedUser;
    }

    @Override
    public void setPasswordIfUpdated(User user, String newPassword) {
        if (newPassword != null && !newPassword.equals(user.getPassword())) {
            setPassword(user, newPassword);
        }
    }

    @Override
    public void setPassword(User user, String newPassword) {
        user.setPassword(passwordEncoder.encode(newPassword));
    }

    @Override
    public User register(User user) {
        return register(user, RoleName.ROLE_USER);
    }

    @Override
    public User register(User user, RoleName roleName) {
        return findByUsername(user.getUsername())
                .orElseGet(() -> processRegistration(user, roleName));
    }

    @Override
    public User register(String email, String password, String name, RoleName roleName) {
        return findByEmail(email)
                .orElseGet(() -> processRegistration(new User(email, password, name), roleName));
    }

    @Override
    public User register(String chatId, String name) {
        return findByChatId(chatId).
                orElseGet(() -> processRegistration(new User(chatId, name), RoleName.ROLE_USER));
    }

    @Override
    public User processRegistration(User user, RoleName roleName) {
        roleManagementService.assignRole(user, roleName);
        Optional.ofNullable(user.getPassword())
                .ifPresent(password -> setPassword(user, password));

        User registeredUser = save(user);
        logger.info("User with ID '{}' registered", registeredUser.getId());
        return registeredUser;
    }

    @Override
    public Optional<User> findById(long id) {
        return userCache.findById(id)
                .or(() -> {
                    Optional<User> user = userRepository.findById(id);
                    user.ifPresent(userCache::put);
                    return user;
                });
    }

    @Override
    public User getById(long id) {
        return findById(id)
                .orElseThrow(() -> new UserNotFoundException(String.format("User with ID %d not found", id)));
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userCache.findByKey(email)
                .or(() -> {
                    Optional<User> user = userRepository.findByEmail(email);
                    user.ifPresent(userCache::put);
                    return user;
                });
    }

    @Override
    public User getByEmail(String email) {
        return findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(String.format("User with email %s not found", email)));
    }

    @Override
    public Optional<User> findByChatId(String chatId) {
        return userCache.findByKey(chatId)
                .or(() -> {
                    Optional<User> user = userRepository.findByChatId(chatId);
                    user.ifPresent(userCache::put);
                    return user;
                });
    }

    @Override
    public User getByChatId(String chatId) {
        return findByChatId(chatId)
                .orElseThrow(() -> new UserNotFoundException(String.format("User with chatId %s not found", chatId)));
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return findByUsername(username, username);
    }

    @Override
    public Optional<User> findByUsername(String email, String chatId) {
        return userCache.findByUsername(chatId, email)
                .or(() -> {
                    Optional<User> user = userRepository.findByChatIdOrEmail(chatId, email);
                    user.ifPresent(userCache::put);
                    return user;
                });
    }

    @Override
    public User getByUsername(String username) {
        return findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(String.format("User with username %s not found", username)));
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(userRepository.findAll());
    }

    @Override
    public void delete(User user) {
        userCache.evict(user);
        userRepository.delete(user);
        logger.info("User with ID '{}' deleted", user.getId());
    }

    @Override
    public void delete(long id) {
        findById(id).ifPresent(this::delete);
    }
}