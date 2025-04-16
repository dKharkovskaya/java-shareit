
package ru.practicum.shareit.item.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemWithBookingInfoDto;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.request.ItemRequest;
import ru.practicum.shareit.user.model.User;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class MapperItem {
    public static ItemDto toItemDto(Item item) {
        ItemDto itemDto = new ItemDto();
        itemDto.setId(item.getId());
        itemDto.setName(item.getName());
        itemDto.setDescription(item.getDescription());
        itemDto.setAvailable(item.getAvailable());
        itemDto.setComments(new ArrayList<Comment>(1));
        if (item.getRequest() != null) {
            itemDto.setRequestId(item.getRequest().getId());
        }
        return itemDto;
    }

    public static Item toItem(ItemDto dto, User owner, ItemRequest request) {
        if (dto == null) {
            return null;
        } else {
            return new Item(null, dto.getName(), dto.getDescription(), dto.getAvailable(), null, dto.getRequestId(), null);
        }
    }

    public static Item toItem(ItemDto dto) {
        if (dto == null) {
            return null;
        } else {
            return new Item(dto.getId(), dto.getName(), dto.getDescription(), dto.getAvailable(), null, dto.getRequestId(), null);
        }
    }

    public static ItemWithBookingInfoDto toItemWithBookingInfoDto(Item item, List<CommentDto> comment) {
        if (item == null) {
            return null;
        }
        return new ItemWithBookingInfoDto(
                item.getId(),
                item.getName(),
                item.getDescription(),
                item.getAvailable(),
                item.getRequest() != null ? item.getRequest().getId() : null,
                null,
                null,
                comment);
    }
}
