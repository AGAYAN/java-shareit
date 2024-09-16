package ru.practicum.shareit.comments.mapper;

import ru.practicum.shareit.comments.ItemAndCommentDto;
import ru.practicum.shareit.item.model.Item;

public interface ItemAndCommentDtoMapper {
    ItemAndCommentDto parseItemInItemAndCommentDto(Item item);

    Item parseItemAndCommentDtoInItem(ItemAndCommentDto itemAndCommentDto);
}
