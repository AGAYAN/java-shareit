package ru.practicum.shareit.booking;

import lombok.Data;
import lombok.NonNull;
import ru.practicum.shareit.item.model.Item;

import java.time.LocalDate;

/**
 * TODO Sprint add-bookings.
 */
@Data
@NonNull
public class Booking {
    private Long id;

    private LocalDate start;

    private LocalDate end;

    private Item item;

    private Long booker;
}
