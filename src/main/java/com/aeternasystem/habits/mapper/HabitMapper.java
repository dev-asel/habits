package com.aeternasystem.habits.mapper;

import com.aeternasystem.habits.persistence.model.Habit;
import com.aeternasystem.habits.persistence.model.User;
import com.aeternasystem.habits.services.UserService;
import com.aeternasystem.habits.web.dto.HabitDTO;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Collections;
import java.util.List;

@Mapper(componentModel = "spring")
public interface HabitMapper {
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "userId", source = "user.id")
    HabitDTO toDTO(Habit habit);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "user", expression = "java(mapUserIdToUser(habitDTO.getUserId(), userService))")
    Habit toEntity(HabitDTO habitDTO, @Context UserService userService);

    default List<HabitDTO> toDTOs(List<Habit> habits) {
        return habits == null ? Collections.emptyList() :
                habits.stream().map(this::toDTO).toList();
    }

    default List<Habit> toEntities(List<HabitDTO> habitDTOs, @Context UserService userService) {
        return habitDTOs == null ? Collections.emptyList() :
                habitDTOs.stream().map(dto -> toEntity(dto, userService)).toList();
    }

    default User mapUserIdToUser(Long userId, @Context UserService userService) {
        return userId == null ? null : userService.getById(userId);
    }
}