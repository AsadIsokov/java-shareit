package ru.practicum.shareit.item.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.practicum.shareit.user.User;

/**
 * TODO Sprint add-controllers.
 */
@Data
@AllArgsConstructor
public class Item {
    private long id;
    private String name;
    private String description;
    private Boolean available;
    private User owner;
    private Long request;

    public Item(long id, String name, String description, Boolean available) {
        this.name = name;
        this.description = description;
        this.available = available;
    }

    public boolean getAvailable() {
        return this.available;
    }

}
