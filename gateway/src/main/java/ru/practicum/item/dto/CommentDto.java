package ru.practicum.item.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import ru.practicum.item.model.Item;

import java.time.LocalDateTime;

@Getter
@Setter
@RequiredArgsConstructor
public class CommentDto {
    private Long id;

    private String text;

    private String authorName;

    private Item item;

    private LocalDateTime created;

    public CommentDto(Long id, String text, Item item, String name, LocalDateTime created) {
        this.id = id;
        this.text = text;
        this.item = item;
        this.authorName = name;
        this.created = created;
    }

    public CommentDto(Long id, String text, Item item, LocalDateTime created) {
        this.id = id;
        this.text = text;
        this.item = item;
        this.created = created;
    }
}
