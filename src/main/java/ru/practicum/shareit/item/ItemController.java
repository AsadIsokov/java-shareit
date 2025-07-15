package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.service.ItemService;

import java.util.Collection;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ItemDto create(
            @RequestHeader("X-Sharer-User-Id") Long userId,
            @RequestBody ItemDto itemDto) {
        log.info("Получен POST-запрос к эндпоинту: '/items' на добавлении вещи!");
        return itemService.create(userId, itemDto);
    }

    @PatchMapping("/{itemId}")
    @ResponseStatus(HttpStatus.OK)
    public ItemDto edit(
            @PathVariable long itemId,
            @RequestHeader("X-Sharer-User-Id") Long userId,
            @RequestBody ItemDto itemDto) {
        log.info("Получен PATCH-запрос к эндпоинту: '/items' на редактировании вещи с itemId={}", itemId);
        return itemService.update(itemId, userId, itemDto);
    }

    @GetMapping("/{itemId}")
    @ResponseStatus(HttpStatus.OK)
    public ItemDto infoById(
            @PathVariable long itemId,
            @RequestHeader("X-Sharer-User-Id") Long userId) {
        log.info("Получен GET-запрос к эндпоинту: '/items' для получении инфо о вещи с itemId={}", itemId);
        return itemService.infoById(itemId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ItemDto> findAllByUserId(@RequestHeader("X-Sharer-User-Id") Long userId) {
        log.info("Получен GET-запрос к эндпоинту: '/items' для получении вещей пользователя с userId={}", userId);
        return itemService.findAllByUserId(userId);
    }

    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public Collection<ItemDto> searchByText(@RequestParam String text) {
        log.info("Получен GET-запрос к эндпоинту: '/items/search' для поиска вещи!");
        return itemService.searchByText(text);
    }
}
