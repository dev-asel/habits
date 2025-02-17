package com.aeternasystem.habits.persistence.repositories;

import com.aeternasystem.habits.persistence.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByChatId(String chatId);
    Optional<User> findByChatIdOrEmail(String chatId, String email);
}