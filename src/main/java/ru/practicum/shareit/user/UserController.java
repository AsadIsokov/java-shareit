package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.service.UserService;

import java.util.Collection;

/**
 * TODO Sprint add-controllers.
 */
@RestController
@RequestMapping(path = "/users")
@Slf4j
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Collection<User> findAll() {
        log.info("Получен GET-запрос к эндпоинту: '/users' на получение пользователей");
        return userService.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User create(@RequestBody User user) {
        log.info("Получен POST-запрос к эндпоинту: '/users' на добавление пользователя");
        return userService.create(user);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public User update(@PathVariable long id, @RequestBody User user) {
        log.info("Получен PATCH-запрос к эндпоинту: '/users' на обновление пользователя с id={}", id);
        return userService.update(id, user);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable long id) {
        log.info("Получен DELETE-запрос к эндпоинту: '/users' на удаление пользователя с id={}", id);
        userService.deleteUser(id);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public User findUserById(@PathVariable long id) {
        log.info("Получен GET-запрос к эндпоинту: '/users' на получение пользователя с id={}", id);
        return userService.getUserById(id);
    }
}
