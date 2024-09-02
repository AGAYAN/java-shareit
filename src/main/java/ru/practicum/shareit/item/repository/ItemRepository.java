package ru.practicum.shareit.item.repository;

import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemRepository {
    ItemDto saveItem(ItemDto item, Long ownerId);

    ItemDto updateItem(Long itemId, ItemDto item, Long ownerId);

    List<Item> findItemsByOwnerId(Long userId);

    Item findItemById(Long itemId);

    List<Item> searchItems(String text, Long ownerId);
}
