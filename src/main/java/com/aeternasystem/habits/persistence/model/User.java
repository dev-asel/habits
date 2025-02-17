package com.aeternasystem.habits.persistence.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    @Size(min = 1)
    private String name;

    @Column(name = "chat_id", unique = true)
    private String chatId;

    @Column(name = "email", unique = true)
    private String email;

    @JsonIgnore
    @Getter
    @Column(name = "password", length = 60)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Habit> habits = new ArrayList<>();

    public User(String email, String password, String name, String chatId) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.chatId = chatId;
    }

    public User(String chatId, String name) {
        this.chatId = chatId;
        this.name = name;
    }

    public User(String chatId, String password, Set<Role> roles) {
        this.chatId = chatId;
        this.password = password;
        this.roles = roles;
    }

    public User(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public User(String name, String email, String password, Set<Role> roles) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

    public String getUsername() {
        return email != null ? email : chatId;
    }
}