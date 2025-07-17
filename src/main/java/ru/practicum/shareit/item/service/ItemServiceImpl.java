package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.WrongDataException;
import ru.practicum.shareit.item.ItemMapper;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemStorage;
    private final UserRepository userStorage;

    private final ItemMapper itemMapper;

    @Override
    public ItemDto create(long userId, ItemDto itemDto) {
        if (userStorage.getUserById(userId) == null) {
            throw new NotFoundException("Пользователь с таким id не существует!");
        }
        if (itemDto.getAvailable() == null) {
            throw new WrongDataException("Значение поле available не может быть null!");
        }
        if (itemDto.getName() == null || itemDto.getName().equals("")) {
            throw new WrongDataException("Значение поле name пустое либо равно null");
        }
        if (itemDto.getDescription() == null) {
            throw new WrongDataException("Значение поле description равно null!");
        }
        Item item = itemMapper.toItem(itemDto);
        item.setOwner(userStorage.getUserById(userId));
        itemStorage.create(item);
        log.info("Вещь добавлена. Владелец - пользователь с id = " + userId);
        return itemMapper.toItemDto(item);
    }

    @Override
    public ItemDto update(long itemId, long userId, ItemDto itemDto) {
        if (itemStorage.findById(itemId).getOwner().getId() != userId) {
            throw new NotFoundException("Пользователь с таким id не является владельцем данный item!");
        }
        if (itemStorage.findById(itemId) == null) {
            throw new NotFoundException("Вещь с таким id не найдена!");
        }
        Item item = itemStorage.findById(itemId);
        if (itemDto.getName() != null) {
            item.setName(itemDto.getName());
        }
        if (itemDto.getDescription() != null) {
            item.setDescription(itemDto.getDescription());
        }
        if (itemDto.getAvailable() != null) {
            item.setAvailable(itemDto.getAvailable());
        }
        item.setOwner(userStorage.getUserById(userId));
        item.setId(itemId);
        Item newItem = itemStorage.updateItem(itemId, item);
        log.info("Вещь с id = " + itemId + " отредактирована!");
        return itemMapper.toItemDto(newItem);
    }

    @Override
    public ItemDto infoById(long id) {
        Item item = itemStorage.findById(id);
        return itemMapper.toItemDto(item);
    }

    @Override
    public List<ItemDto> findAllByUserId(Long userId) {
        log.info("Все вещи пользователя с id = " + userId);
        return itemStorage.findAll().stream()
                .filter(item -> item.getOwner().getId() == userId)
                .map(itemMapper::toItemDto)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<ItemDto> searchByText(String text) {
        if (text == null || text.isBlank()) {
            return Collections.emptyList();
        }
        String searchText = text.toLowerCase();
        log.info("Поиск вещи по имени или описании");
        return itemStorage.findAll().stream()
                .filter(Item::getAvailable)
                .filter(item ->
                        (item.getName() != null && item.getName().toLowerCase().contains(searchText)) ||
                                (item.getDescription() != null && item.getDescription().toLowerCase().contains(searchText))
                )
                .map(itemMapper::toItemDto)
                .collect(Collectors.toList());
    }
}
