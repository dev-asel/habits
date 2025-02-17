package com.aeternasystem.habits.services.impl;

import com.aeternasystem.habits.exception.HabitLogAlreadyExistsException;
import com.aeternasystem.habits.exception.HabitLogNotFoundException;
import com.aeternasystem.habits.persistence.model.Habit;
import com.aeternasystem.habits.persistence.model.HabitLog;
import com.aeternasystem.habits.persistence.repositories.HabitLogRepository;
import com.aeternasystem.habits.services.HabitLogService;
import com.aeternasystem.habits.services.HabitService;
import com.aeternasystem.habits.services.cache.HabitLogCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class HabitLogServiceImpl implements HabitLogService {

    private static final Logger logger = LoggerFactory.getLogger(HabitLogServiceImpl.class);

    private final HabitLogRepository habitLogRepository;
    private final HabitService habitService;
    private final HabitLogCache habitLogCache;

    public HabitLogServiceImpl(HabitLogRepository habitLogRepository, @Lazy HabitService habitService, HabitLogCache habitLogCache) {
        this.habitLogRepository = habitLogRepository;
        this.habitService = habitService;
        this.habitLogCache = habitLogCache;
    }

    @Override
    public HabitLog save(HabitLog habitLog) {
        HabitLog savedHabitLog = habitLogRepository.save(habitLog);
        habitLogCache.put(savedHabitLog);
        return savedHabitLog;
    }

    @Override
    public HabitLog createHabitLog(HabitLog habitLog) {
        HabitLog savedHabitLog = save(habitLog);
        logger.info("HabitLog with ID '{}' created", savedHabitLog.getId());
        return savedHabitLog;
    }

    @Override
    public HabitLog create(HabitLog habitLog) {
        Long id = habitLog.getId();
        if (findById(id).isPresent()) {
            throw new HabitLogAlreadyExistsException("HabitLog with ID " + id + " already exists");
        }
        return createHabitLog(habitLog);
    }

    @Override
    public HabitLog create(Long habitId, LocalDate date) {
        checkHabitLogNotExists(habitId, date);
        Habit habit = habitService.getById(habitId);
        return createHabitLog(new HabitLog(habit, date));
    }

    @Override
    public HabitLog updateHabitLog(Long id, Habit newHabit, LocalDate date) {
        HabitLog existingHabitLog = getById(id);
        Habit habit = newHabit != null ? newHabit : existingHabitLog.getHabit();
        checkHabitLogNotExists(habit.getId(), date);

        existingHabitLog.setDate(date);
        if (newHabit != null && !newHabit.equals(existingHabitLog.getHabit())) {
            existingHabitLog.setHabit(habit);
        }

        HabitLog updatedHabitLog = save(existingHabitLog);
        logger.info("HabitLog with ID '{}' updated", updatedHabitLog.getId());
        return updatedHabitLog;
    }

    @Override
    public HabitLog update(HabitLog habitLog) {
        return updateHabitLog(habitLog.getId(), habitLog.getHabit(), habitLog.getDate());
    }

    @Override
    public HabitLog updateDate(Long id, LocalDate date) {
        return updateHabitLog(id, null, date);
    }

    @Override
    public void checkHabitLogNotExists(Long habitId, LocalDate date) {
        if (findByHabitIdAndDate(habitId, date).isPresent()) {
            throw new HabitLogAlreadyExistsException("HabitLog with date " + date + " and habitId " + habitId + " already exists");
        }
    }

    @Override
    public Optional<HabitLog> findById(Long id) {
        return habitLogCache.findById(id)
                .or(() -> {
                    Optional<HabitLog> habitLog = habitLogRepository.findById(id);
                    habitLog.ifPresent(habitLogCache::put);
                    return habitLog;
                });
    }

    @Override
    public HabitLog getById(Long id) {
        return findById(id)
                .orElseThrow(() -> new HabitLogNotFoundException("HabitLog with ID " + id + " not found"));
    }

    @Override
    public Optional<HabitLog> findByHabitIdAndDate(Long habitId, LocalDate date) {
        return habitLogRepository.findByHabit_IdAndDate(habitId, date);
    }

    @Override
    public HabitLog getByHabitIdAndDate(Long habitId, LocalDate date) {
        return findByHabitIdAndDate(habitId, date)
                .orElseThrow(() -> new HabitLogNotFoundException("HabitLog with habitId " + habitId + " and date " + date + " not found"));
    }

    @Override
    public List<HabitLog> findAllByHabitId(Long habitId) {
        return habitLogRepository.findByHabit_Id(habitId);
    }

    @Override
    public List<HabitLog> findAllByUserId(Long userId) {
        return habitLogRepository.findByHabit_User_Id(userId);
    }

    @Override
    public List<HabitLog> findAll() {
        return habitLogRepository.findAll();
    }

    @Override
    public void delete(HabitLog habitLog) {
        habitLogCache.evict(habitLog);
        habitLogRepository.delete(habitLog);
        logger.info("HabitLog with ID '{}' deleted", habitLog.getId());
    }

    @Override
    public void delete(Long id) {
        findById(id).ifPresent(this::delete);
    }

    @Override
    public void delete(Long habitId, LocalDate date) {
        findByHabitIdAndDate(habitId, date).ifPresent(this::delete);
    }

    @Override
    public void deleteByHabitId(Long habitId) {
        findAllByHabitId(habitId).forEach(this::delete);
        logger.info("All HabitLogs for Habit ID '{}' have been deleted", habitId);
    }

    @Override
    public List<HabitLog> findByUserIdForToday(Long userId) {
        return habitLogRepository.findByHabitIdAndDate(userId, LocalDate.now());
    }

    @Override
    public List<HabitLog> findByHabitIdAndMonth(Long habitId, YearMonth yearMonth) {
        return habitLogRepository.findByHabitIdAndMonth(habitId, yearMonth.getYear(), yearMonth.getMonthValue());
    }

}