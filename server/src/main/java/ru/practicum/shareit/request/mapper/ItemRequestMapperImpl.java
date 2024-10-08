package ru.practicum.shareit.request.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.request.ItemRequest;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.dto.ItemResponsDtoOwner;

@Component
public class ItemRequestMapperImpl {

    public static ItemRequestDto parseItemRequestDto(ItemRequest itemRequest) {
        ItemRequestDto itemRequestDto = new ItemRequestDto();
        itemRequestDto.setId(itemRequest.getId());
        itemRequestDto.setDescription(itemRequest.getDescription());
        itemRequestDto.setCreated(itemRequest.getCreated());
        return itemRequestDto;
    }

    public static ItemResponsDtoOwner parseItemInItemResponseDto(Item item) {
        ItemResponsDtoOwner itemResponseDTO = new ItemResponsDtoOwner();

        itemResponseDTO.setId(item.getId());
        itemResponseDTO.setName(item.getName());
        itemResponseDTO.setOwnerId(item.getOwner());

        return itemResponseDTO;
    }

}