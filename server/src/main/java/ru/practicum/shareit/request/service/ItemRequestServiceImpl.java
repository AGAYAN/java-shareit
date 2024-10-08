package ru.practicum.shareit.request.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exeption.NotFoundException;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.request.ItemRequest;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.dto.ItemResponsDtoOwner;
import ru.practicum.shareit.request.mapper.ItemRequestMapperImpl;
import ru.practicum.shareit.request.repository.ItemRequestRepository;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ItemRequestServiceImpl implements ItemRequestService {

    private final ItemRequestRepository itemRequestRepository;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    @Override
    public ItemRequestDto createRequest(ItemRequestDto itemRequestDto, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Нету такого user"));

        ItemRequest request = new ItemRequest();
        request.setDescription(itemRequestDto.getDescription());
        request.setRequester(user);
        request.setCreated(LocalDateTime.now());

        ItemRequest savedRequest = itemRequestRepository.save(request);
        return ItemRequestMapperImpl.parseItemRequestDto(savedRequest);
    }

    @Override
    public List<ItemRequestDto> getOwnRequests(Long userId) {
         userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Нету такого user"));

        List<ItemRequest> request = itemRequestRepository.findAllByRequesterIdOrderByCreatedDesc(userId);
        return request.stream()
                .map(ItemRequestMapperImpl::parseItemRequestDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ItemRequestDto> getAllRequests() {
        List<ItemRequest> itemRequest =  itemRequestRepository.findAll();

        return itemRequest.stream()
                .map(ItemRequestMapperImpl::parseItemRequestDto)
                .toList();
    }

    @Override
    public ItemRequestDto getRequestById(Long requestId) {
        ItemRequest itemRequest = itemRequestRepository.findById(requestId).orElseThrow(() -> new NotFoundException("Ошибка в коде"));
        System.out.println(requestId);
        ItemRequestDto itemRequestDto = ItemRequestMapperImpl.parseItemRequestDto(itemRequest);

        List<ItemResponsDtoOwner> itemResponseDtoList = itemRepository.getItemByRequestId(requestId).stream()
                .map(ItemRequestMapperImpl::parseItemInItemResponseDto)
                .toList();

        itemRequestDto.setItems(itemResponseDtoList);

        return itemRequestDto;
    }

    @Override
    public ItemRequest validate(Long requestId) {
        return itemRequestRepository.findById(requestId).orElseThrow(() -> new NotFoundException("нету такого user"));
    }
}
