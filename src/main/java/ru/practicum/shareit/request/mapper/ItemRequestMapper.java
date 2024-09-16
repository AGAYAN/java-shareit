package ru.practicum.shareit.request.mapper;

import ru.practicum.shareit.request.ItemRequest;
import ru.practicum.shareit.request.dto.ItemRequestDto;

public interface ItemRequestMapper {
    ItemRequest parseItemRequest(ItemRequestDto itemRequestDto);

    ItemRequestDto parseItemRequestDto(ItemRequest itemRequest);
}
