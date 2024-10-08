package ru.practicum.item.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import ru.practicum.request.model.ItemRequest;

@Getter
@Setter
@RequiredArgsConstructor
@EqualsAndHashCode(exclude = {"id"})
public class Item {
    private Long id;

    private String name;

    private String description;

    private Boolean available;

    private Long owner;

    private ItemRequest request;
}
