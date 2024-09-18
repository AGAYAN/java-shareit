package ru.practicum.shareit.item.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import ru.practicum.shareit.item.model.Item;

@Getter
@Setter
@RequiredArgsConstructor
@EqualsAndHashCode(exclude = {"id"}, callSuper = true)
public class ItemDto extends Item {

    private Long id;

    @NotBlank(message = "Name cannot be null or empty")
    private String name;

    @NotBlank(message = "Description cannot be null or empty")
    private String description;

    @NotNull(message = "Available field cannot be null")
    private Boolean available;

    public ItemDto(Long id, String name, String description, Boolean available) {
        this.id = id;
        this.name = name;
        this.available = available;
        this.description = description;
    }
}
