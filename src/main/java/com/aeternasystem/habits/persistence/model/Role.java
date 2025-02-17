package com.aeternasystem.habits.persistence.model;

import com.aeternasystem.habits.util.enums.RoleName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "roles")
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", unique = true, nullable = false)
    @Enumerated(EnumType.STRING)
    private RoleName name;

    public Role(RoleName name) {
        this.name = name;
    }

    @JsonIgnore
    @Override
    public String getAuthority() {
        return getName().name();
    }
}