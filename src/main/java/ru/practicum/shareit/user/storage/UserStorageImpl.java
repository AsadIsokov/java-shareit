package ru.practicum.shareit.user.storage;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.exception.ConflictException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.WrongDataException;
import ru.practicum.shareit.user.User;

import java.util.*;

@Repository
@Slf4j
public class UserStorageImpl implements UserStorage {

    private final Map<Long, User> users = new HashMap<>();

    private long getNextId() {
        long currentMaxId = users.keySet()
                .stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }

    @Override
    public Collection<User> findAll() {
        log.info("Находим всех пользователей!");
        return users.values();
    }

    @Override
    public User create(User user) {
        users.values().stream()
                .filter(elem -> user.getEmail().equals(elem.getEmail()))
                .findFirst()
                .ifPresent(elem -> {
                    throw new ConflictException("Этот имейл уже используется");
                });
        if (!isValidEmail(user.getEmail())) {
            throw new WrongDataException("Неправильный формат электронной почты!");
        }
        user.setId(getNextId());
        users.put(user.getId(), user);
        log.info("Добавление пользователя!");
        return user;

    }

    private boolean isValidEmail(String email) {
        return EmailValidator.getInstance().isValid(email);
    }

    @Override
    public User update(long id, User user) {
        if (!users.containsKey(id)) {
            throw new NotFoundException("Пользователь с таким id не найден!");
        }
        User oldUser = users.get(id);
        if (!users.get(id).getEmail().equals(user.getEmail()) && user.getEmail() != null) {
            users.values().stream()
                    .filter(elem -> user.getEmail().equals(elem.getEmail()))
                    .findFirst()
                    .ifPresent(elem -> {
                        throw new ConflictException("Этот имейл уже используется");
                    });
            oldUser.setEmail(user.getEmail());
        }

        if (user.getName() != null) {
            oldUser.setName(user.getName());
        }
        users.put(user.getId(), oldUser);
        log.info("Редактирование пользователя с id={}", id);
        return users.get(oldUser.getId());
    }

    @Override
    public void deleteUser(long id) {
        if (users.containsValue(users.get(id))) {
            log.info("Удаляем пользователя с id={}", id);
            users.remove(id, users.get(id));
        }
    }

    @Override
    public User getUserById(long userId) {
        log.info("Получаем пользователя с id={}", userId);
        return users.get(userId);
    }
}
