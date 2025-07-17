package ru.practicum.shareit.item.repository;

import ru.practicum.shareit.item.model.Item;

import java.util.Collection;

public interface ItemRepository {
    Item create(Item item);

    Item findById(long id);

    Item updateItem(long id, Item item);

    Collection<Item> findAll();
}
