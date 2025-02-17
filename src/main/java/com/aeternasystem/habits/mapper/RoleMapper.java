package com.aeternasystem.habits.mapper;

import com.aeternasystem.habits.persistence.model.Role;
import com.aeternasystem.habits.web.dto.RoleDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring" )
public interface RoleMapper {
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    RoleDTO toDTO(Role role);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    Role toEntity(RoleDTO roleDTO);

    default Set<RoleDTO> toDTOs(Set<Role> roles) {
        return roles == null ? Collections.emptySet() :
                roles.stream().map(this::toDTO).collect(Collectors.toSet());
    }

    default Set<Role> toEntities(Set<RoleDTO> roleDTOs) {
        return roleDTOs == null ? Collections.emptySet() :
                roleDTOs.stream().map(this::toEntity).collect(Collectors.toSet());
    }
}