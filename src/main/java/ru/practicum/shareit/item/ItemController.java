package ru.practicum.shareit.item;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.comments.CommentsDto;
import ru.practicum.shareit.comments.ItemAndCommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.mapper.ItemMapperImpl;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.service.ItemService;

import java.util.List;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
@Slf4j
public class ItemController {

    private static final String USER_ID_HEADER = "X-Sharer-User-Id";
    private final ItemService itemService;

    @PostMapping
    public ItemDto addNewItem(@RequestBody Item item,
                              @RequestHeader(USER_ID_HEADER) Long ownerId) {
        itemService.createItem(item, ownerId);
        return ItemMapperImpl.parseItemNoDto(item);
    }


    @PatchMapping("/{itemId}")
    public ItemDto updateItem(@PathVariable("itemId") @Positive Long itemId,
                              @RequestBody Item item,
                              @RequestHeader(USER_ID_HEADER) @NotNull Long ownerId) {
        log.info("Получен запрос на обновление предмета с id = {}", itemId);
        log.info("Данные предмета: имя = {}, описание = {}, доступность = {}",
                item.getName(), item.getDescription(), item.getAvailable());
        itemService.updateItem(itemId, item, ownerId);
        return ItemMapperImpl.parseItemNoDto(item);
    }

    @GetMapping("/{itemId}")
    public ItemAndCommentDto getItemById(@PathVariable("itemId") Long itemId) {
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
        return itemService.search(text, ownerId);
    }

    @PostMapping("/{itemId}/comment")
    public CommentsDto createNewComment(@PathVariable("itemId") Long itemId,
                                        @RequestBody CommentsDto commentDto,
                                        @RequestHeader(USER_ID_HEADER) Long ownerId) {
        return itemService.addNewComment(itemId, commentDto, ownerId);
    }
}
