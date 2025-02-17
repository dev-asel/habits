package com.aeternasystem.habits.persistence.repositories;

import com.aeternasystem.habits.persistence.model.Role;
import com.aeternasystem.habits.util.enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName roleName);
}