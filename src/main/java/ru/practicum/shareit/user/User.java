package ru.practicum.shareit.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * TODO Sprint add-controllers.
 */
@Data
public class User {
    private long id;
    private String name;
    @NotNull
    @Email(message = "Email должен быть валидным")
    private String email;
}
