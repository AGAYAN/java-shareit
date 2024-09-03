package ru.practicum.shareit.item;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.service.ItemService;

import java.util.List;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {

    private static final Logger log = LoggerFactory.getLogger(ItemController.class);
    private static final String USER_ID_HEADER = "X-Sharer-User-Id";
    private final ItemService itemService;

    @PostMapping
    public ItemDto addNewItem(@Valid @RequestBody ItemDto item,
                              @RequestHeader(USER_ID_HEADER) Long ownerId) {
        log.info("Происходит добавление");
        return itemService.createItem(item, ownerId);
    }

    @PatchMapping("/{itemId}")
    public ItemDto updateItem(@PathVariable("itemId") @Positive Long itemId,
                              @RequestBody ItemDto item,
                              @RequestHeader(USER_ID_HEADER) @NotNull Long ownerId) {
        log.info("Происходит изменение");
        return itemService.updateItem(itemId, item, ownerId);
    }

    @GetMapping("/{itemId}")
    public Item getItemById(@PathVariable("itemId") Long itemId) {
        log.info("Происходит поиск по id:{}", itemId);
        return itemService.fetchItemById(itemId);
    }

    @GetMapping
    public List<Item> getItemsBuOwnerId(@RequestHeader(USER_ID_HEADER) Long ownerId) {
        log.info("Происходит поиск по idOwner");
        return itemService.fetchItemsByOwnerId(ownerId);
    }

    @GetMapping("/search")
    public List<Item> search(@RequestParam String text,
                             @RequestHeader(USER_ID_HEADER) Long ownerId) {
        return itemService.searchItems(text, ownerId);
    }
}
