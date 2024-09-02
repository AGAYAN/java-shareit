package ru.practicum.shareit.item.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.practicum.shareit.item.model.Item;

/**
 * TODO Sprint add-controllers.
 */
@Data
@EqualsAndHashCode(exclude = {"id"}, callSuper = true)
public class ItemDto extends Item {

    private Long id;

    @NotBlank(message = "Name cannot be null or empty")
    private String name;

    @NotNull
    private String description;

    @NotNull
    private Boolean available;

}
