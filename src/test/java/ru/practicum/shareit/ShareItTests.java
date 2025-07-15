package ru.practicum.shareit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.WrongDataException;
import ru.practicum.shareit.item.ItemController;
import ru.practicum.shareit.item.ItemMapper;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.service.ItemServiceImpl;
import ru.practicum.shareit.item.storage.ItemStorageImpl;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserController;
import ru.practicum.shareit.user.service.UserServiceImpl;
import ru.practicum.shareit.user.storage.UserStorageImpl;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ShareItTests {

    private UserStorageImpl userStorage;
    private UserServiceImpl userService;
    private UserController userController;

    private ItemStorageImpl itemStorage;
    private ItemServiceImpl itemService;
    private ItemController itemController;

    @BeforeEach
    void setUp() {
        userStorage = new UserStorageImpl();
        userService = new UserServiceImpl(userStorage);
        userController = new UserController(userService);

        itemStorage = new ItemStorageImpl();
        ItemMapper itemMapper = new ItemMapper();
        itemService = new ItemServiceImpl(itemStorage, userStorage, itemMapper);
        itemController = new ItemController(itemService);
    }

    private User createTestUser(String name, String email) {
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        return userStorage.create(user);
    }

    private ItemDto createTestItemDto() {
        ItemDto itemDto = new ItemDto();
        itemDto.setName("Test Item");
        itemDto.setDescription("Test Description");
        itemDto.setAvailable(true);
        return itemDto;
    }

    @Test
    void testUserCreateAndGet() {
        User user = new User();
        user.setName("Test User");
        user.setEmail("test@example.com");

        User createdUser = userService.create(user);
        assertNotNull(createdUser.getId());

        User foundUser = userService.getUserById(createdUser.getId());
        assertEquals(createdUser, foundUser);
    }

    @Test
    void testUserUpdate() {
        User original = createTestUser("Original", "original@example.com");
        User updates = new User();
        updates.setName("Updated");
        updates.setEmail("updated@example.com");
        User updated = userService.update(original.getId(), updates);
        assertEquals("Updated", updated.getName());
        assertEquals("updated@example.com", updated.getEmail());
    }

    @Test
    void testItemCreateAndGet() {
        User owner = createTestUser("Owner", "owner@example.com");
        ItemDto itemDto = createTestItemDto();
        ItemDto createdItem = itemService.create(owner.getId(), itemDto);
        assertNotNull(createdItem.getId());
        ItemDto foundItem = itemService.infoById(createdItem.getId());
        assertEquals(createdItem.getId(), foundItem.getId());
    }

    @Test
    void testItemCreateWithInvalidData() {
        User owner = createTestUser("Owner", "owner@example.com");
        ItemDto invalidItem = new ItemDto();
        assertThrows(WrongDataException.class, () -> itemService.create(owner.getId(), invalidItem));
    }

    @Test
    void testItemUpdate() {
        User owner = createTestUser("Owner", "owner@example.com");
        ItemDto original = itemService.create(owner.getId(), createTestItemDto());
        ItemDto updates = new ItemDto();
        updates.setName("Updated Name");
        updates.setAvailable(false);
        ItemDto updated = itemService.update(original.getId(), owner.getId(), updates);
        assertEquals("Updated Name", updated.getName());
        assertFalse(updated.getAvailable());
    }

    @Test
    void testUserItemsIntegration() {
        User user = createTestUser("Integration User", "integration@test.com");
        ItemDto item1 = itemService.create(user.getId(), createTestItemDto());
        ItemDto item2 = createTestItemDto();
        item2.setName("Second Item");
        itemService.create(user.getId(), item2);
        List<ItemDto> userItems = itemService.findAllByUserId(user.getId());
        assertEquals(2, userItems.size());
        User foundUser = userService.getUserById(user.getId());
        assertEquals("Integration User", foundUser.getName());
    }

    @Test
    void testItemOwnershipValidation() {
        User owner = createTestUser("Owner", "owner@test.com");
        User nonOwner = createTestUser("Non-Owner", "nonowner@test.com");
        ItemDto item = itemService.create(owner.getId(), createTestItemDto());
        ItemDto updates = new ItemDto();
        updates.setName("Unauthorized Update");
        assertThrows(NotFoundException.class, () ->
                itemService.update(item.getId(), nonOwner.getId(), updates));
    }
}
