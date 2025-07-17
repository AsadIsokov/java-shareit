package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.dto.ItemDto;

import java.util.Collection;
import java.util.List;

public interface ItemService {
    ItemDto create(long userId, ItemDto itemDto);

    ItemDto update(long itemId, long userId, ItemDto itemDto);

    ItemDto infoById(long id);

    List<ItemDto> findAllByUserId(Long userId);

    Collection<ItemDto> searchByText(String text);
}
