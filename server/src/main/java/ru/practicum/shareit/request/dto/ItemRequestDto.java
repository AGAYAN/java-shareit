package ru.practicum.shareit.request.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@EqualsAndHashCode(exclude = {"id"})
public class ItemRequestDto {
    private Long id;

    private String description;

    private LocalDateTime created;

    private List<ItemResponsDtoOwner> items = new ArrayList<>();
}
