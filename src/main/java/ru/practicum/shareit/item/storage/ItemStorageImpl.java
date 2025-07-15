package ru.practicum.shareit.item.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.model.Item;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Repository
@Slf4j
public class ItemStorageImpl implements ItemStorage {
    private final Map<Long, Item> items = new HashMap<>();

    private long getNextId() {
        long currentMaxId = items.keySet()
                .stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }

    @Override
    public Item create(Item item) {
        item.setId(getNextId());
        items.put(item.getId(), item);
        log.info("Вещь добавлена на репозиторий!");
        return item;
    }

    @Override
    public Item findById(long id) {
        if (!items.containsKey(id)) {
            throw new NotFoundException("Вещь с таким id = " + id + " не найден!");
        }
        return items.get(id);
    }

    @Override
    public Item updateItem(long id, Item item) {
        Item oldItem = findById(id);
        if (item.getName() != null) {
            oldItem.setName(item.getName());
        }
        if (item.getDescription() != null) {
            oldItem.setDescription(item.getDescription());
        }
        oldItem.setAvailable(item.getAvailable());
        items.put(id, oldItem);
        return oldItem;
    }

    @Override
    public Collection<Item> findAll() {
        return items.values();
    }
}
