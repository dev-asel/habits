package com.aeternasystem.habits.web.dto;

import com.aeternasystem.habits.validation.group.*;
import com.aeternasystem.habits.validation.validators.EmailAndPassword;
import com.aeternasystem.habits.validation.validators.EmailOrChatId;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@EmailOrChatId(groups = {Create.class, Update.class})
@EmailAndPassword(groups = {Create.class, Update.class})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    @JsonProperty("id")
    @NotNull(groups = Update.class)
    @Positive(groups = Update.class)
    private Long id;

    @JsonProperty("name")
    @NotNull(groups = Create.class)
    @Size(max = 100, groups = {Update.class, Create.class})
    private String name;

    @JsonProperty("chat_id")
    @NotNull(groups = TelegramCreate.class)
    @Size(max = 50, groups = {Update.class, Create.class})
    @Pattern(regexp = "^-?\\d+$", groups = {Create.class, Update.class, Delete.class})
    private String chatId;

    @JsonProperty("email")
    @NotNull(groups = EmailCreate.class)
    @Email(groups = {Update.class, Create.class, Delete.class})
    @Size(min = 6, max = 254, groups = {Update.class, Create.class, Delete.class})
    @Pattern(regexp = "^[a-zA-Z0-9][a-zA-Z0-9._%+-]{0,63}@[a-zA-Z0-9.-]{1,253}\\.[a-zA-Z]{2,}$\n", groups = {Update.class, Create.class, Delete.class})
    private String email;

    @JsonProperty("password")
    @NotNull(groups = EmailCreate.class)
    @Size(max = 100, groups = {Update.class, Create.class})
    private String password;

    @JsonProperty("role_ids")
    private Set<Long> roleIds;
}