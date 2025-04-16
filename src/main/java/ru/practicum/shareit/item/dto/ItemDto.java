package ru.practicum.shareit.item.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.booking.dto.BookingResponseShortDto;
import ru.practicum.shareit.item.mapper.MapperComment;
import ru.practicum.shareit.item.model.Comment;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ItemDto {
    private Integer id;
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    @NotNull
    private Boolean available;
    private Integer requestId;
    private BookingResponseShortDto lastBooking;
    private BookingResponseShortDto nextBooking;
    private List<CommentDto> comments;

    public void setComments(List<Comment> comments) {
        this.comments = comments.stream().map(MapperComment::toDtoResponse).collect(Collectors.toList());
    }
}