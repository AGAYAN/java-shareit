package ru.practicum.shareit.item.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * TODO Sprint add-controllers.
 */
@Data
@EqualsAndHashCode(exclude = {"id"})
public class Item {
    private Long id;

    private String name;

    private String description;

    private Boolean available;

    private Long owner;

    private Long request;
}
