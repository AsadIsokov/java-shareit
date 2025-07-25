package ru.practicum.shareit.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ConflictException extends IllegalArgumentException {
    public ConflictException(String message) {
        super(message);
        log.error(message);
    }
}
