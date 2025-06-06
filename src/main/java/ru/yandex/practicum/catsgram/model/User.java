package ru.yandex.practicum.catsgram.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.Instant;

@Data
@EqualsAndHashCode(of = "email")
@AllArgsConstructor
@Builder
public class User {
    Long id;
    String username;
    String email;
    String password;
    Instant registrationDate;
}
