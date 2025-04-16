package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemWithBookingInfoDto;
import ru.practicum.shareit.item.model.Comment;

import java.util.List;

public interface ItemService {
    ItemDto create(ItemDto dto, Integer userId);

    ItemDto update(ItemDto dto, Integer itemId, Integer userId);

    ItemWithBookingInfoDto getById(Integer id);

    List<ItemDto> findAllByUser(Integer id);

    List<ItemDto> searchItems(String text, Integer from, Integer size);

    Comment createComment(Comment comment, int itemId, int userId);
}