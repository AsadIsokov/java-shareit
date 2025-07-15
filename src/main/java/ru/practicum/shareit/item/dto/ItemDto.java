package ru.practicum.shareit.item.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * TODO Sprint add-controllers.
 */
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class ItemDto {
    long id;
    private String name;
    private String description;
    private Boolean available;
}
