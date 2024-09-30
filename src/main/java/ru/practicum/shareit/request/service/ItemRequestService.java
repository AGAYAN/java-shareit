package ru.practicum.shareit.request.service;

import ru.practicum.shareit.request.ItemRequest;

import java.util.List;

public interface ItemRequestService {
    List<ItemRequest> fetchItemById(Long id);
}
