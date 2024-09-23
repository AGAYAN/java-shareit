package ru.practicum.shareit.comments;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.item.model.Item;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Getter
@Setter
public class ItemAndCommentDto extends Item {

    Long id;

    String name;

    String description;

    Boolean available;

    Long owner;

    Long request;

    LocalDateTime lastBooking;

    LocalDateTime nextBooking;

    List<CommentsDto> comments;

    List<Comments> commentsList;

    List<Booking> bookings;

    public ItemAndCommentDto(Long id, String name, String description, Boolean available, Long owner, Long request) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.available = available;
        this.owner = owner;
        this.request = request;
        comments = new ArrayList<>();
    }

    public ItemAndCommentDto(Item item, List<Booking> bookings, List<Comments> comments) {
        this.id = item.getId();
        this.bookings = bookings;
        this.commentsList = comments;
    }
}
