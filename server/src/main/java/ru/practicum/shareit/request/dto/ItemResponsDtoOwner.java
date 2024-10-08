package ru.practicum.shareit.request.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class ItemResponsDtoOwner {
    private Long id;

    private String name;

    private Long ownerId;
}
