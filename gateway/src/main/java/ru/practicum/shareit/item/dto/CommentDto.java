package ru.practicum.shareit.item.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;


@AllArgsConstructor
@Getter
@Setter
@Builder
@NoArgsConstructor
public class CommentDto {

    @NotBlank
    private String text;
}