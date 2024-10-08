package ru.practicum.item;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.item.dto.CommentDto;
import ru.practicum.item.dto.ItemDto;

@RestController
@RequiredArgsConstructor
@RequestMapping("/items")
public class ItemController {
    private final ItemClient itemClient;
    private static final String USER_ID_HEADER = "X-Sharer-User-Id";

    @PostMapping
    public ResponseEntity<Object> addNewItem(@RequestBody ItemDto item,
                                             @RequestHeader(USER_ID_HEADER) Long ownerId) {
        return itemClient.addNewItem(ownerId, item);
    }

    @PostMapping("/{itemId}/comment")
    public ResponseEntity<Object> createNewComment(@PathVariable("itemId") Long itemId,
                                        @RequestBody @Valid CommentDto commentDto,
                                        @RequestHeader(USER_ID_HEADER) Long ownerId) {
        return itemClient.createNewComment(itemId, commentDto, ownerId);
    }

    @PatchMapping("/{itemId}")
    public ResponseEntity<Object> updateItem(@PathVariable("itemId") Long itemId,
                                             @RequestBody ItemDto itemDto,
                                             @RequestHeader(USER_ID_HEADER) Long ownerId) {
        return itemClient.updateItem(itemId, itemDto, ownerId);
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<Object> getItemById(@PathVariable("itemId") Long itemId,
                                              @RequestHeader(USER_ID_HEADER) Long ownerId) {
        return itemClient.getItemById(itemId, ownerId);
    }

    @GetMapping
    public ResponseEntity<Object> getItemsBuOwnerId(@RequestHeader(USER_ID_HEADER) Long ownerId) {
        return itemClient.getItemByOwnerId(ownerId);
    }

    @GetMapping("/search")
    public ResponseEntity<Object> search(@RequestParam String text,
                                         @RequestHeader(USER_ID_HEADER) Long ownerId) {
        return itemClient.search(text, ownerId);
    }

}
