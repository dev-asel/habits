package com.aeternasystem.habits.web.dto;

import com.aeternasystem.habits.validation.group.Create;
import com.aeternasystem.habits.validation.group.CustomCreate;
import com.aeternasystem.habits.validation.group.Delete;
import com.aeternasystem.habits.validation.group.Update;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class HabitLogDTO {
    @JsonProperty("id")
    @NotNull(groups = Update.class)
    @Positive(groups = Update.class)
    private Long id;

    @JsonProperty("habit_id")
    @NotNull(groups = Create.class)
    @Positive(groups = Create.class)
    private Long habitId;

    @JsonProperty("date")
    @NotNull(groups = {Create.class, CustomCreate.class, Update.class, Delete.class})
    private LocalDate date;
}