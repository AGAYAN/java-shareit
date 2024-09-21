package ru.practicum.shareit.item.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.comments.Comments;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class ItemDetails {
    private Item item; // The item itself
    private List<Booking> bookings; // List of bookings associated with the item
    private List<Comments> comments; // List of comments associated with the item

    // Constructor
    public ItemDetails(Item item, List<Booking> bookings, List<Comments> comments) {
        this.item = item;
        this.bookings = bookings;
        this.comments = comments;
    }
}
