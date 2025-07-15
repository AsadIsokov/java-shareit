package ru.practicum.shareit.item.storage;

import ru.practicum.shareit.item.model.Item;

import java.util.Collection;

public interface ItemStorage {
    Item create(Item item);

    Item findById(long id);

    Item updateItem(long id, Item item);

    Collection<Item> findAll();
}
