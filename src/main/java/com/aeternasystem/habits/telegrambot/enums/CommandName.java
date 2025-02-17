package com.aeternasystem.habits.telegrambot.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CommandName {
    START("/start"),
    INFO("/info"),
    APP("/app"),
    WEB("/web");

    private final String command;
}