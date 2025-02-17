package com.aeternasystem.habits.services.impl;

import com.aeternasystem.habits.exception.HabitAlreadyExistsException;
import com.aeternasystem.habits.exception.HabitNotFoundException;
import com.aeternasystem.habits.persistence.model.Habit;
import com.aeternasystem.habits.persistence.model.User;
import com.aeternasystem.habits.persistence.repositories.HabitRepository;
import com.aeternasystem.habits.services.HabitService;
import com.aeternasystem.habits.services.UserService;
import com.aeternasystem.habits.services.cache.HabitCache;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class HabitServiceImpl implements HabitService {

    private static final Logger logger = LoggerFactory.getLogger(HabitServiceImpl.class);

    private final HabitRepository habitRepository;
    private final UserService userService;
    private final HabitCache habitCache;

    @PersistenceContext
    private EntityManager entityManager;

    public HabitServiceImpl(HabitRepository habitRepository, UserService userService, HabitCache habitCache) {
        this.habitRepository = habitRepository;
        this.userService = userService;
        this.habitCache = habitCache;
    }

    @Override
    public Habit save(Habit habit) {
        Habit savedHabit = habitRepository.save(habit);
        habitCache.put(savedHabit);
        return savedHabit;
    }

    @Override
    public Habit createHabit(Habit habit) {
        Habit savedHabit = save(habit);
        logger.info("Habit with ID '{}' created", savedHabit.getId());
        return savedHabit;
    }


    @Override
    public Habit create(Habit habit) {
        if (findById(habit.getId()).isPresent()) {
            throw new HabitAlreadyExistsException("Habit with ID " + habit.getId() + " already exists");
        }
        return createHabit(habit);
    }

    @Override
    public Habit create(Long userId, String name) {
        checkHabitNotExists(userId, name);
        User user = userService.getById(userId);
        return createHabit(new Habit(user, name));
    }

    @Override
    public Habit updateHabit(Long id, User newUser, String name) {
        Habit existingHabit = getById(id);
        User user = newUser != null ? newUser : existingHabit.getUser();
        checkHabitNotExists(user.getId(), name);

        existingHabit.setName(name);
        if (newUser != null && !newUser.equals(existingHabit.getUser())) {
            existingHabit.setUser(user);
        }

        Habit updatedHabit = save(existingHabit);
        logger.info("Habit with ID '{}' updated", updatedHabit.getId());
        return updatedHabit;
    }

    @Override
    public Habit update(Habit habit) {
        return updateHabit(habit.getId(), habit.getUser(), habit.getName());
    }


    @Override
    public Habit updateName(Long id, String name) {
        return updateHabit(id, null, name);
    }

    @Override
    public void checkHabitNotExists(Long userId, String name) {
        if (findByUserIdAndName(userId, name).isPresent()) {
            throw new HabitAlreadyExistsException("Habit with user ID " + userId + " and habit name " + name + " already exists");
        }
    }

    @Override
    public Optional<Habit> findById(Long id) {
        return habitCache.findById(id)
                .or(() -> {
                    Optional<Habit> habit = habitRepository.findById(id);
                    habit.ifPresent(habitCache::put);
                    return habit;
                });
    }

    @Override
    public Optional<Habit> findByIdWithLogs(Long id) {
        return habitRepository.findByIdWithLogs(id);
    }

    @Override
    public Habit getById(Long id) {
        return findById(id)
                .orElseThrow(() -> new HabitNotFoundException("Habit with id " + id + " not found"));
    }

    @Override
    public List<Habit> findByUserId(Long userId) {
        return habitRepository.findByUser_Id(userId);
    }

    @Override
    public Optional<Habit> findByUserIdAndName(Long userId, String name) {
        return habitRepository.findByUser_IdAndName(userId, name);
    }

    @Override
    public Optional<Habit> findByUserIdAndNameWithLogs(Long userId, String name) {
        return habitRepository.findByUserIdAndNameWithLogs(userId, name);
    }

    @Override
    public Habit getByUserIdAndName(Long userId, String name) {
        return findByUserIdAndName(userId, name)
                .orElseThrow(() -> new HabitNotFoundException("Habit not found"));
    }

    @Override
    public List<Habit> findAll() {
        return habitRepository.findAll();
    }

    @Override
    public void delete(Habit habit) {
        habitCache.evict(habit);
        habitRepository.delete(habit);
        logger.info("Habit with ID '{}' deleted", habit.getId());
    }

    @Override
    public void delete(Long id) {
        findByIdWithLogs(id).ifPresent(this::delete);
    }

    @Override
    public void delete(Long userId, String name) {
        findByUserIdAndNameWithLogs(userId, name).ifPresent(this::delete);
    }
}