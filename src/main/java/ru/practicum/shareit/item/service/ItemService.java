package ru.practicum.shareit.item.service;

import ru.practicum.shareit.comments.CommentsDto;
import ru.practicum.shareit.comments.ItemAndCommentDto;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemService {
    Item createItem(Item item, Long ownerId);

    Item updateItem(Long itemId, Item item, Long ownerId);

    List<ItemAndCommentDto> fetchItemsByOwnerId(Long userId);

    ItemAndCommentDto fetchItemById(Long itemId);

    List<Item> search(String text, Long ownerId);

    CommentsDto addNewComment(Long itemId, CommentsDto commentDto, Long userId);
}
