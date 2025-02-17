package com.aeternasystem.habits.web.controllers;

import com.aeternasystem.habits.mapper.HabitMapper;
import com.aeternasystem.habits.services.HabitService;
import com.aeternasystem.habits.services.UserService;
import com.aeternasystem.habits.validation.group.Create;
import com.aeternasystem.habits.validation.group.Update;
import com.aeternasystem.habits.web.dto.HabitDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class HabitRestController {

    private static final Logger logger = LoggerFactory.getLogger(HabitRestController.class);

    private final HabitService habitService;
    private final HabitMapper habitMapper;
    private final UserService userService;

    public HabitRestController(HabitService habitService, HabitMapper habitMapper, UserService userService) {
        this.habitService = habitService;
        this.habitMapper = habitMapper;
        this.userService = userService;
    }

    @GetMapping("/habits")
    public ResponseEntity<List<HabitDTO>> getAllHabits() {
        return ResponseEntity.ok(habitMapper.toDTOs(habitService.findAll()));
    }

    @GetMapping("users/{userId}/habits")
    public ResponseEntity<List<HabitDTO>> getHabitsByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(habitMapper.toDTOs(habitService.findByUserId(userId)));
    }

    @GetMapping("/habits/{id}")
    public ResponseEntity<HabitDTO> getHabitById(@PathVariable Long id) {
        return ResponseEntity.ok(habitMapper.toDTO(habitService.getById(id)));
    }

    @PostMapping("/habits")
    public ResponseEntity<HabitDTO> create(@Validated(Create.class) @RequestBody HabitDTO habitDTO) {
        return ResponseEntity.ok(habitMapper.toDTO(habitService.create(habitDTO.getUserId(), habitDTO.getName())));
    }

    @PutMapping("/habits/{id}")
    public ResponseEntity<HabitDTO> update(@PathVariable Long id, @Validated(Update.class) @RequestBody HabitDTO habitDTO) {
        return ResponseEntity.ok(habitMapper.toDTO(habitService.update(habitMapper.toEntity(habitDTO, userService))));
    }

    @PatchMapping("/habits/{id}")
    public ResponseEntity<HabitDTO> updateName(@PathVariable Long id, @Validated(Update.class) @RequestBody HabitDTO habitDTO) {
        return ResponseEntity.ok(habitMapper.toDTO(habitService.updateName(habitDTO.getId(), habitDTO.getName())));
    }

    @DeleteMapping("/habits/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        habitService.delete(id);
        return ResponseEntity.noContent().build();
    }
}