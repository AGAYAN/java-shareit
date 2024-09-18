package ru.practicum.shareit.comments;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import ru.practicum.shareit.item.model.Item;

import java.time.LocalDateTime;

@Getter
@Setter
@RequiredArgsConstructor
@EqualsAndHashCode(exclude = {"id"})
public class CommentsDto {
    private Long id;

    private String text;

    private String authorName;

    private Item item;

    private LocalDateTime created;

    public CommentsDto(Long id, String text, Item item, String name, LocalDateTime created) {
        this.id = id;
        this.text = text;
        this.item = item;
        this.authorName = name;
        this.created = created;
    }

    public CommentsDto(Long id, String text, LocalDateTime created) {
        this.id = id;
        this.text = text;
        this.created = created;
    }
}
