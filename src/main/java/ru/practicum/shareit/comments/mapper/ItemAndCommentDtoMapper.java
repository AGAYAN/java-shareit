package ru.practicum.shareit.comments.mapper;

import ru.practicum.shareit.comments.ItemAndCommentDto;
import ru.practicum.shareit.item.model.Item;

public class ItemAndCommentDtoMapper {

    public static ItemAndCommentDto parseItemInItemAndCommentDto(Item item) {
        return new ItemAndCommentDto(item.getId(),
                item.getName(),
                item.getDescription(),
                item.getAvailable(),
                item.getOwner(),
                item.getRequest());
    }

    public static Item parseItemAndCommentDtoInItem(ItemAndCommentDto itemAndCommentDto) {
        Item item = new Item();

        item.setId(itemAndCommentDto.getId());
        item.setName(itemAndCommentDto.getName());
        item.setDescription(itemAndCommentDto.getDescription());
        item.setAvailable(itemAndCommentDto.getAvailable());
        item.setOwner(itemAndCommentDto.getOwner());
        item.setRequest(itemAndCommentDto.getRequest());

        return item;
    }
}