package ru.yandex.practicum.catsgram.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ParameterNotValidException extends IllegalArgumentException {
    private String parameter;
    private String reason;
}
