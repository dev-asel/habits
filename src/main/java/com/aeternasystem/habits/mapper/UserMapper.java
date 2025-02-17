package com.aeternasystem.habits.mapper;

import com.aeternasystem.habits.persistence.model.Role;
import com.aeternasystem.habits.persistence.model.User;
import com.aeternasystem.habits.services.RoleService;
import com.aeternasystem.habits.web.dto.UserDTO;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "chatId", source = "chatId")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "roleIds", expression = "java(mapRolesToRoleIds(user.getRoles()))")
    UserDTO toDTO(User user);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "chatId", source = "chatId")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "roles", expression = "java(mapRoleIdsToRoles(userDTO.getRoleIds(), roleService))")
    User toEntity(UserDTO userDTO, @Context RoleService roleService);

    default List<UserDTO> toDTOs(List<User> users) {
        return users == null ? Collections.emptyList() :
                users.stream().map(this::toDTO).toList();
    }

    default List<User> toEntities(List<UserDTO> userDTOs, @Context RoleService roleService) {
        return userDTOs == null ? Collections.emptyList() :
                userDTOs.stream().map(dto -> toEntity(dto, roleService)).toList();
    }

    default Set<Role> mapRoleIdsToRoles(Set<Long> roleIds, @Context RoleService roleService) {
        return roleIds == null ? Collections.emptySet() :
                roleIds.stream().map(roleService::getById).collect(Collectors.toSet());
    }

    default Set<Long> mapRolesToRoleIds(Set<Role> roles) {
        return roles == null ? Collections.emptySet() :
                roles.stream().map(Role::getId).collect(Collectors.toSet());
    }
}