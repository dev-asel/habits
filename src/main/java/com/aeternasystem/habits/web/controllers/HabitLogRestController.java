package com.aeternasystem.habits.web.controllers;

import com.aeternasystem.habits.mapper.HabitLogMapper;
import com.aeternasystem.habits.security.authentication.model.CustomUserDetails;
import com.aeternasystem.habits.services.HabitLogService;
import com.aeternasystem.habits.services.HabitService;
import com.aeternasystem.habits.validation.group.Create;
import com.aeternasystem.habits.validation.group.CustomCreate;
import com.aeternasystem.habits.validation.group.Delete;
import com.aeternasystem.habits.validation.group.Update;
import com.aeternasystem.habits.web.dto.HabitLogDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.YearMonth;
import java.util.List;

@RestController
@RequestMapping("/api")
public class HabitLogRestController {

    private static final Logger logger = LoggerFactory.getLogger(HabitLogRestController.class);

    private final HabitLogService habitLogService;
    private final HabitLogMapper habitLogMapper;
    private final HabitService habitService;

    public HabitLogRestController(HabitLogService habitLogService, HabitLogMapper habitLogMapper, HabitService habitService) {
        this.habitLogService = habitLogService;
        this.habitLogMapper = habitLogMapper;
        this.habitService = habitService;
    }

    @GetMapping("/logs")
    public ResponseEntity<List<HabitLogDTO>> getAll() {
        return ResponseEntity.ok(habitLogMapper.toDTOs(habitLogService.findAll()));
    }

    @GetMapping("/habits/{habitId}/logs")
    public ResponseEntity<List<HabitLogDTO>> getByHabitId(@PathVariable Long habitId) {
        return ResponseEntity.ok(habitLogMapper.toDTOs(habitLogService.findAllByHabitId(habitId)));
    }

    @GetMapping("/logs/{id}")
    public ResponseEntity<HabitLogDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(habitLogMapper.toDTO(habitLogService.getById(id)));
    }

    @GetMapping("/users/user/logs")
    public ResponseEntity<List<HabitLogDTO>> getByUserId(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok(habitLogMapper.toDTOs(habitLogService.findAllByUserId(userDetails.getUserId())));
    }

    @PostMapping("/logs")
    public ResponseEntity<HabitLogDTO> create(@Validated(Create.class) @RequestBody HabitLogDTO habitLogDTO) {
        return ResponseEntity.ok(habitLogMapper.toDTO(habitLogService.create(habitLogDTO.getHabitId(), habitLogDTO.getDate())));
    }

    @PostMapping("/habits/{habitId}/logs")
    public ResponseEntity<HabitLogDTO> create(@PathVariable Long habitId, @Validated(CustomCreate.class) @RequestBody HabitLogDTO habitLogDTO) {
        return ResponseEntity.ok(habitLogMapper.toDTO(habitLogService.create(habitId, habitLogDTO.getDate())));
    }

    @PutMapping("/logs/{id}")
    public ResponseEntity<HabitLogDTO> update(@PathVariable Long id, @Validated(Update.class) @RequestBody HabitLogDTO habitLogDTO) {
        return ResponseEntity.ok(habitLogMapper.toDTO(habitLogService.update(habitLogMapper.toEntity(habitLogDTO, habitService))));
    }

    @PatchMapping("/logs/{id}")
    public ResponseEntity<HabitLogDTO> updateDate(@PathVariable Long id, @Validated(Update.class) @RequestBody HabitLogDTO habitLogDTO) {
        return ResponseEntity.ok(habitLogMapper.toDTO(habitLogService.updateDate(habitLogDTO.getId(), habitLogDTO.getDate())));
    }

    @DeleteMapping("/logs/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        habitLogService.delete(id);
        return ResponseEntity.ok("Habit log deleted successfully with ID " + id);
    }

    @DeleteMapping("/habits/{habitId}/logs")
    public ResponseEntity<String> deleteByHabitIdAndDate(@PathVariable Long habitId, @Validated(Delete.class) @RequestBody HabitLogDTO habitLogDTO) {
        habitLogService.delete(habitId, habitLogDTO.getDate());
        return ResponseEntity.ok("Habit log deleted successfully for " + habitLogDTO.getDate());
    }

    @GetMapping("/users/user/logs/today")
    public ResponseEntity<List<HabitLogDTO>> getByUserIdForToday(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok(habitLogMapper.toDTOs(habitLogService.findByUserIdForToday(userDetails.getUserId())));
    }

    @GetMapping("/{habitId}/logs/{yearMonth}")
    public ResponseEntity<List<HabitLogDTO>> getByHabitIdAndMonth(@PathVariable Long habitId, @PathVariable YearMonth yearMonth) {
        return ResponseEntity.ok(habitLogMapper.toDTOs(habitLogService.findByHabitIdAndMonth(habitId, yearMonth)));
    }
}