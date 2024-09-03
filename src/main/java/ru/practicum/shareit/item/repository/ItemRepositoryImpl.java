package ru.practicum.shareit.item.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Item;

import java.util.*;
import java.util.regex.Pattern;


@Repository
@RequiredArgsConstructor
public class ItemRepositoryImpl implements ItemRepository {

    private final Map<Long, Item> itemMap = new HashMap<>();
    private final ItemMapper itemMapper;
    private long currentId = 0;

    @Override
    public ItemDto saveItem(ItemDto item, Long ownerId) {
        item.setId(nextValue());
        Item item1 = itemMapper.parseItem(item);
        item1.setOwner(ownerId);
        itemMap.put(item1.getId(), item1);
        return itemMapper.parseItemNoDto(item1);
    }

    @Override
    public ItemDto updateItem(Long itemId, ItemDto item, Long ownerId) {
        Item item1 = findItemById(itemId);
        validation(item, item1);
        return itemMapper.parseItemNoDto(item1);
    }

    @Override
    public List<Item> findItemsByOwnerId(Long ownerId) {
        return itemMap.values().stream()
                .filter(elem -> Objects.equals(elem.getOwner(), ownerId))
                .toList();
    }

    @Override
    public Item findItemById(Long itemId) {
        return itemMap.values().stream()
                .filter(elem -> Objects.equals(elem.getId(), itemId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Неверный айди придмета"));
    }

    @Override
    public List<Item> searchItems(String text, Long ownerId) {
        return findItemsByOwnerId(ownerId).stream()
                .filter(elem -> (Pattern.matches("^" + text.toLowerCase() + "$", elem.getName().toLowerCase()) ||
                        Pattern.matches("^" + text.toLowerCase() + "$", elem.getDescription().toLowerCase())) &&
                        elem.getAvailable())
                .toList();
    }

    private long nextValue() {
        return ++currentId;
    }

    public void validation(ItemDto item, Item item1) {
        if (Objects.nonNull(item.getName())) {
            item1.setName(item.getName());
        }

        if (Objects.nonNull(item.getDescription())) {
            item1.setDescription(item.getDescription());
        }

        if (Objects.nonNull(item.getAvailable())) {
            item1.setAvailable(item.getAvailable());
        }
    }
}