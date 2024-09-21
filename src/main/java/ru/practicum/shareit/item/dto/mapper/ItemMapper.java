package ru.practicum.shareit.item.dto.mapper;

import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

public class ItemMapper {

    public static Item parseItem(ItemDto itemDto) {
        Item item = new Item();

        item.setId(itemDto.getId());
        item.setDescription(itemDto.getDescription());
        item.setName(itemDto.getName());
        item.setAvailable(itemDto.getAvailable());

        return item;
    }

    public static ItemDto parseItemNoDto(Item item) {
        ItemDto itemDto = new ItemDto();

        itemDto.setId(item.getId());
        itemDto.setDescription(item.getDescription());
        itemDto.setName(item.getName());
        itemDto.setAvailable(item.getAvailable());

        return itemDto;
    }
}
