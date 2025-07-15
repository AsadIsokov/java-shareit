package ru.practicum.shareit.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WrongDataException extends IllegalArgumentException {
    public WrongDataException(String message) {
        super(message);
        log.error(message);
    }
}
