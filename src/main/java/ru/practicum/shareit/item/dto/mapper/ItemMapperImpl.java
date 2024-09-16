package ru.practicum.shareit.item.dto.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

@Component
public class ItemMapperImpl implements ItemMapper {

    @Override
    public Item parseItem(ItemDto itemDto) {
        Item item = new Item();

        item.setId(itemDto.getId());
        item.setDescription(itemDto.getDescription());
        item.setName(itemDto.getName());
        item.setAvailable(itemDto.getAvailable());

        return item;
    }

    @Override
    public ItemDto parseItemNoDto(Item item) {
        ItemDto itemDto = new ItemDto();

        itemDto.setId(item.getId());
        itemDto.setDescription(item.getDescription());
        itemDto.setName(item.getName());
        itemDto.setAvailable(item.getAvailable());

        return itemDto;
    }
}
