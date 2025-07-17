package ru.practicum.shareit.user.service;

import ru.practicum.shareit.user.User;

import java.util.Collection;

public interface UserService {
    Collection<User> findAll();

    User create(User user);

    User update(long id, User user);

    void deleteUser(long id);

    User getUserById(long id);
}
