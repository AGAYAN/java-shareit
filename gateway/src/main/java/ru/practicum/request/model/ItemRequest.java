package ru.practicum.request.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import ru.practicum.user.model.User;

import java.time.LocalDateTime;

@Getter
@Setter
@RequiredArgsConstructor
public class ItemRequest {
    private Long id;

    private String description;

    private User requester;

    private LocalDateTime created = LocalDateTime.now();
}