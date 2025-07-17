package ru.practicum.shareit.user.repository;

import ru.practicum.shareit.user.User;

import java.util.Collection;

public interface UserRepository {
    Collection<User> findAll();

    User create(User user);

    User update(long id, User user);

    void deleteUser(long id);

    User getUserById(long userId);
}
