package com.aeternasystem.habits.web.dto;

import com.aeternasystem.habits.util.enums.RoleName;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.checkerframework.checker.index.qual.Positive;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoleDTO {
    @JsonProperty("id")
    @NotNull
    @Positive
    private Long id;

    @JsonProperty("name")
    @NotNull
    @Size(min = 1, max = 255)
    private RoleName name;
}