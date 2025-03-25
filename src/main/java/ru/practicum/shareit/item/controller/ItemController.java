package ru.practicum.shareit.item.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.ItemServiceImpl;
import ru.practicum.shareit.item.dto.ItemDto;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/items")
public class ItemController {
    private static final String USER_ID = "X-Sharer-User-Id";

    private final ItemServiceImpl itemService;

    @PostMapping
    public ItemDto create(@Valid @RequestBody ItemDto dto, @RequestHeader(USER_ID) Integer userId) {
        return itemService.create(dto, userId);
    }

    @PatchMapping("/{itemId}")
    public ItemDto update(@RequestBody ItemDto dto, @PathVariable Integer itemId, @RequestHeader(USER_ID) Integer userId) {
        return itemService.update(dto, itemId, userId);
    }

    @GetMapping("/{itemId}")
    public ItemDto getById(@PathVariable Integer itemId) {
        return itemService.getById(itemId);
    }

    @GetMapping
    public List<ItemDto> getAllByUser(@RequestHeader(USER_ID) Integer userId) {
        return itemService.findAllByUser(userId);
    }

    @GetMapping("/search")
    public List<ItemDto> getFreeItemByKeyword(@RequestParam String text) {
        return itemService.findFreeItemByKeyword(text);
    }
}