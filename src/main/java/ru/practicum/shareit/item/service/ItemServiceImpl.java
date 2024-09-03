package ru.practicum.shareit.item.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exeption.NotFoundException;
import ru.practicum.shareit.exeption.ValidationException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.service.UserService;

import java.util.List;
import java.util.Objects;


@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final UserService userService;

    @Override
    public ItemDto createItem(@Valid ItemDto newItem, Long ownerId) {
        userService.getUserById(ownerId);
        return itemRepository.saveItem(newItem, ownerId);
    }

    @Override
    public ItemDto updateItem(Long id, @Valid ItemDto updatedItem, Long ownerId) {
        userService.getUserById(ownerId);
        Item existingItem = fetchItemById(id);
        if (!Objects.equals(existingItem.getOwner(), ownerId)) {
            throw new ValidationException("Вам не разрешается изменять этот элемент");
        }
        return itemRepository.updateItem(id, updatedItem, ownerId);
    }

    @Override
    public List<Item> fetchItemsByOwnerId(Long ownerId) {
        userService.getUserById(ownerId);
        return itemRepository.findItemsByOwnerId(ownerId);
    }

    @Override
    public Item fetchItemById(Long id) {
        Item item = itemRepository.findItemById(id);
        if (item == null) {
            throw new NotFoundException("Элемент с идентификатором = " + id + " не найден");
        }
        return item;
    }

    @Override
    public List<Item> searchItems(String query, Long ownerId) {
        userService.getUserById(ownerId);
        return itemRepository.searchItems(query, ownerId);
    }
}