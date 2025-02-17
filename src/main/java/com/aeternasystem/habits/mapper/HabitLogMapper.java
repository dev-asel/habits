package com.aeternasystem.habits.mapper;

import com.aeternasystem.habits.persistence.model.Habit;
import com.aeternasystem.habits.persistence.model.HabitLog;
import com.aeternasystem.habits.services.HabitService;
import com.aeternasystem.habits.web.dto.HabitLogDTO;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Collections;
import java.util.List;

@Mapper(componentModel = "spring")
public interface HabitLogMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "habitId", source = "habit.id")
    @Mapping(target = "date", source = "date")
    HabitLogDTO toDTO(HabitLog habitLog);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "date", source = "date")
    @Mapping(target = "habit", expression = "java(mapHabitIdToHabit(habitLogDTO.getHabitId(), habitService))")
    HabitLog toEntity(HabitLogDTO habitLogDTO, @Context HabitService habitService);

    default List<HabitLogDTO> toDTOs(List<HabitLog> habitLogs) {
        return habitLogs == null ? Collections.emptyList() :
                habitLogs.stream().map(this::toDTO).toList();
    }

    default List<HabitLog> toEntities(List<HabitLogDTO> habitLogDTOs, @Context HabitService habitService) {
        return habitLogDTOs == null ? Collections.emptyList() :
                habitLogDTOs.stream().map(dto -> toEntity(dto, habitService)).toList();
    }

    default Habit mapHabitIdToHabit(Long habitId, @Context HabitService habitService) {
        return habitId == null ? null : habitService.getById(habitId);
    }
}