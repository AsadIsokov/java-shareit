package ru.practicum.shareit.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.user.dto.UserDto;

@Component
@Slf4j
public class UserMapper {
    public static UserDto toUserDto(User user) {
        log.info("Превращаем User в UserDto!");
        return new UserDto(
                user.getName(),
                user.getEmail()
        );
    }
}
