package ru.practicum.item.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@EqualsAndHashCode(exclude = {"id"})
public class ItemDto {

    private Long id;

    private String name;

    private String description;

    private Boolean available;

    private Long requestId;
}