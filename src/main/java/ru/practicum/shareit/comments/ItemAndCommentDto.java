package ru.practicum.shareit.comments;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Getter
@Setter
public class ItemAndCommentDto {

    Long id;

    String name;

    String description;

    Boolean available;

    Long owner;

    Long request;

    LocalDateTime lastBooking;

    LocalDateTime nextBooking;

    List<Comments> comments;

    public ItemAndCommentDto(Long id, String name, String description, Boolean available, Long owner, Long request) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.available = available;
        this.owner = owner;
        this.request = request;
        comments = new ArrayList<>();
    }
}
