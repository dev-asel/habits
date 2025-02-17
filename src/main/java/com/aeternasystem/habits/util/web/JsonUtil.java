package com.aeternasystem.habits.util.web;

import com.aeternasystem.habits.exception.InvalidJsonException;
import com.aeternasystem.habits.exception.JsonConversionException;
import com.aeternasystem.habits.persistence.model.Role;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class JsonUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private JsonUtil() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static Map<String, Object> jsonToMap(String jsonString) {
        if (jsonString == null || jsonString.isBlank()) {
            return Collections.emptyMap();
        }
        try {
            return objectMapper.readValue(jsonString, new TypeReference<Map<String, Object>>() {
            });
        } catch (JsonProcessingException e) {
            throw new InvalidJsonException("Invalid JSON format", e);
        }
    }

    public static Map<String, String> jsonToStringMap(String jsonString) {
        return jsonToMap(jsonString).entrySet().stream()
                .filter(entry -> entry.getValue() != null && !entry.getValue().toString().isBlank())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().toString()
                ));
    }

    public static String rolesToJson(Set<Role> roles) {
        if (roles == null || roles.isEmpty()) {
            return "[]";
        }
        try {
            return objectMapper.writeValueAsString(roles);
        } catch (JsonProcessingException e) {
            throw new JsonConversionException("Error converting roles to JSON", e);
        }
    }

    public static Set<Role> jsonToRoles(String rolesJson) {
        if (rolesJson == null || rolesJson.isBlank()) {
            return Collections.emptySet();
        }
        try {
            return objectMapper.readValue(rolesJson, objectMapper.getTypeFactory().constructCollectionType(Set.class, Role.class));
        } catch (JsonProcessingException e) {
            throw new JsonConversionException("Error converting JSON to roles", e);
        }
    }
}