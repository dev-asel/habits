package com.aeternasystem.habits.web.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RedirectResponse {
    @JsonProperty("success")
    @NotNull
    private boolean success;
    @JsonProperty("redirect_url")
    @NotNull
    private String redirectUrl;
}