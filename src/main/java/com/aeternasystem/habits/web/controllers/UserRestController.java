package com.aeternasystem.habits.web.controllers;

import com.aeternasystem.habits.mapper.UserMapper;
import com.aeternasystem.habits.services.RoleService;
import com.aeternasystem.habits.services.UserService;
import com.aeternasystem.habits.validation.group.Create;
import com.aeternasystem.habits.validation.group.Update;
import com.aeternasystem.habits.web.dto.UserDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserRestController {

    private final UserService userService;
    private final UserMapper userMapper;
    private final RoleService roleService;

    public UserRestController(UserService userService, UserMapper userMapper, RoleService roleService) {
        this.userService = userService;
        this.userMapper = userMapper;
        this.roleService = roleService;
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAll() {
        List<UserDTO> users = userMapper.toDTOs(userService.findAll());
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(userMapper.toDTO(userService.getById(id)));
    }

    @PostMapping
    public ResponseEntity<UserDTO> create(@Validated(Create.class) @RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(userMapper.toDTO(userService.create(userMapper.toEntity(userDTO, roleService))));
    }

    @PostMapping("/register" )
    public ResponseEntity<Object> register(@Validated(Create.class) @RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(userMapper.toDTO(userService.register(userMapper.toEntity(userDTO, roleService))));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> update(@PathVariable Long id, @Validated(Update.class) @RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(userMapper.toDTO(userService.update(userMapper.toEntity(userDTO, roleService))));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}