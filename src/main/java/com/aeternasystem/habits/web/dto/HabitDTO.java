package com.aeternasystem.habits.web.dto;

import com.aeternasystem.habits.validation.group.Create;
import com.aeternasystem.habits.validation.group.Update;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HabitDTO {
    @JsonProperty("id")
    @NotNull(groups = Update.class)
    @Positive(groups = Update.class)
    private Long id;

    @JsonProperty("name")
    @NotNull(groups = {Update.class, Create.class})
    @Size(min = 1, max = 255, groups = {Update.class, Create.class})
    private String name;

    @JsonProperty("user_id")
    @NotNull(groups = Create.class)
    @Positive(groups = {Update.class, Create.class})
    private Long userId;
}