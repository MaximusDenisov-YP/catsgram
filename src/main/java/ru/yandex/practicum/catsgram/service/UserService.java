package ru.yandex.practicum.catsgram.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.catsgram.exception.ConditionsNotMetException;
import ru.yandex.practicum.catsgram.exception.DuplicatedDataException;
import ru.yandex.practicum.catsgram.model.User;

import java.time.Instant;
import java.util.*;

@Service
public class UserService {
    private final Map<Long, User> users = new HashMap<>();

    public Collection<User> getUsers() {
        return users.values();
    }

    public Optional<User> findUserById(Long id) {
        return Optional.ofNullable(users.get(id));
    }

    public User createUser(User user) {
        if (user.getEmail().isBlank() || user.getEmail() == null) {
            throw new ConditionsNotMetException("Имейл должен быть указан");
        } else if (users.values().stream().anyMatch(user1 -> user1.getEmail().equals(user.getEmail()))) {
            throw new DuplicatedDataException("Этот имейл уже используется");
        }
        user.setId(getNextId());
        user.setRegistrationDate(Instant.now());
        users.put(user.getId(), user);
        return user;
    }

    public User updateUser(User user) {
        if (user.getId() == null) {
            throw new ConditionsNotMetException("Id должен быть указан");
        } else {
            Optional<User> findedUser = users.values().stream()
                    // Найдем текущего пользователя по ID
                    .filter(u -> u.getId().equals(user.getId()))
                    .findFirst();
            findedUser.ifPresent(
                    user1 -> {
                        if (user.getEmail() != null && !user1.getEmail().equals(user.getEmail())) {
                            if (users.values().stream()
                                    .anyMatch(u ->
                                            !u.getId().equals(user.getId())
                                                    && Objects.equals(u.getEmail(), user.getEmail())
                                    )) {
                                throw new DuplicatedDataException("Этот имейл уже используется");
                            }
                            user1.setEmail(user.getEmail());
                        }
                        if (user.getUsername() != null) {
                            user1.setUsername(user.getUsername());
                        }
                        if (user.getPassword() != null) {
                            user1.setPassword(user.getPassword());
                        }
                    });
            return findedUser.orElse(null);
        }
    }

    private long getNextId() {
        long currentMaxId = users.keySet()
                .stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }
}
