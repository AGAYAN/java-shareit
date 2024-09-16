package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemService {
    ItemDto createItem(ItemDto item, Long ownerId);

    ItemDto updateItem(Long itemId, ItemDto item, Long ownerId);

    List<Item> fetchItemsByOwnerId(Long userId);

    Item fetchItemById(Long itemId);

    List<Item> searchItems(String text, Long ownerId);
}
