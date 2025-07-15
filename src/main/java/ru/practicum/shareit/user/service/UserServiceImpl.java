package ru.practicum.shareit.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.storage.UserStorage;

import java.util.Collection;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserStorage userStorage;

    @Override
    public Collection<User> findAll() {
        log.info("Находим всех пользователей!");
        return userStorage.findAll();
    }

    @Override
    public User create(User user) {
        log.info("Добавление пользователя!");
        return userStorage.create(user);
    }

    @Override
    public User update(long id, User user) {
        log.info("Редактирование пользователя с id={}", id);
        return userStorage.update(id, user);
    }

    @Override
    public void deleteUser(long id) {
        log.info("Удаляем пользователя с id={}", id);
        userStorage.deleteUser(id);
    }

    @Override
    public User getUserById(long id) {
        log.info("Получаем пользователя с id={}", id);
        return userStorage.getUserById(id);
    }

}
