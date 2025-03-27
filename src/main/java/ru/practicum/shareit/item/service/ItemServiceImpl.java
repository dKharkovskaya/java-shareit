package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.error.exception.NotFoundException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.mapper.MapperItem;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.service.UserServiceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final Map<Integer, Item> items = new HashMap<>();
    private final UserServiceImpl userService;

    @Override
    public ItemDto create(ItemDto dto, Integer userId) {
        User owner = userService.getById(userId);
        Item item = MapperItem.toItem(dto, owner, null);
        item.setId(getNextId());
        items.put(item.getId(), item);
        return MapperItem.toItemDto(item);
    }

    @Override
    public ItemDto update(ItemDto dto, Integer itemId, Integer userId) {
        dto.setId(itemId);
        Integer itemDtoId = dto.getId();
        String name = dto.getName();
        String description = dto.getDescription();
        Boolean available = dto.getAvailable();
        checkItem(itemDtoId);
        Item item = items.get(itemDtoId);
        checkOwner(item, userId);
        if (name != null) {
            item.setName(name);
        }
        if (description != null) {
            item.setDescription(description);
        }
        if (available != null) {
            item.setAvailable(available);
        }
        return MapperItem.toItemDto(items.get(itemDtoId));
    }

    @Override
    public ItemDto getById(Integer id) {
        checkItem(id);
        return MapperItem.toItemDto(items.get(id));
    }

    @Override
    public List<ItemDto> findAllByUser(Integer userId) {
        userService.getById(userId);

        return items.values().stream()
                .filter(item -> item.getOwner().getId() == userId)
                .collect(Collectors.toList()).stream().map(MapperItem::toItemDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ItemDto> findFreeItemByKeyword(String text) {
        if (text.isBlank()) {
            return new ArrayList<>();
        }
        return items.values().stream()
                .filter(item -> item.getAvailable().equals(true))
                .filter(item -> item.getName().toLowerCase().contains(text.toLowerCase()) ||
                        item.getDescription().toLowerCase().contains(text.toLowerCase()))
                .collect(Collectors.toList()).stream()
                .map(MapperItem::toItemDto)
                .collect(Collectors.toList());

    }

    // вспомогательный метод для генерации идентификатора нового поста
    private int getNextId() {
        int currentMaxId = items.keySet().stream().mapToInt(id -> id).max().orElse(0);
        return ++currentMaxId;
    }

    private void checkItem(Integer id) {
        if (!items.containsKey(id)) {
            throw new NotFoundException("Item not found");
        }
    }

    private void checkOwner(Item item, Integer ownerId) {
        if (item.getOwner().getId() != ownerId) {
            throw new NotFoundException("The user not found");
        }
    }
}