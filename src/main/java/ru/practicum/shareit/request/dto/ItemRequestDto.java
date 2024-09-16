package ru.practicum.shareit.request.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * TODO Sprint add-item-requests.
 */
@Getter
@Setter
@RequiredArgsConstructor
@EqualsAndHashCode(exclude = {"id"})
public class ItemRequestDto {
    private Long id;

    private String description;
}
