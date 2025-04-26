package ru.practicum.shareit.error.exception;

import org.springframework.web.bind.annotation.ResponseBody;

@ResponseBody()
public class ValidationException extends RuntimeException {
    public ValidationException(String message) {
        super(message);
    }
}
