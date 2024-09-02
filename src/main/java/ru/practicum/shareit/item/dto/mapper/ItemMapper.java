package ru.practicum.shareit.item.dto.mapper;

import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

public interface ItemMapper {
    Item parseItem(ItemDto itemDto);

    ItemDto parseItemNoDto(Item item);
}
