package ru.practicum.shareit.item.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemWithBookingInfoDto;
import ru.practicum.shareit.item.mapper.MapperComment;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.item.dto.ItemDto;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/items")
public class ItemController {
    private static final String USER_ID = "X-Sharer-User-Id";

    private final ItemService itemService;

    @PostMapping
    public ItemDto create(@Valid @RequestBody ItemDto dto, @RequestHeader(USER_ID) Integer userId) {
        return itemService.create(dto, userId);
    }

    @PatchMapping("/{itemId}")
    public ItemDto update(@RequestBody ItemDto dto, @PathVariable Integer itemId, @RequestHeader(USER_ID) Integer userId) {
        return itemService.update(dto, itemId, userId);
    }

    @GetMapping("/{itemId}")
    public ItemWithBookingInfoDto getById(@PathVariable Integer itemId) {
        return itemService.getById(itemId);
    }

    @GetMapping
    public List<ItemDto> getAllByUser(@RequestHeader(USER_ID) Integer userId) {
        return itemService.findAllByUser(userId);
    }

    @GetMapping("/search")
    public List<ItemDto> searchItems( @RequestParam(required = false, defaultValue = "") String text,
                                               @RequestParam(defaultValue = "0") Integer from,
                                               @RequestParam(defaultValue = "10") Integer size) {
        return itemService.searchItems(text, from, size);
    }

    @PostMapping("/{itemId}/comment")
    public CommentDto createComment(
            @RequestHeader("X-Sharer-User-Id") Integer ownerId,
            @RequestBody @Valid CommentDto commentDto,
            @PathVariable Integer itemId
    ) {
        Comment addedComment = itemService.createComment(MapperComment.toComment(commentDto), itemId, ownerId);
        return MapperComment.toDtoResponse(addedComment);
    }
}