package ru.practicum.shareit.booking.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * TODO Sprint add-bookings.
 */
@Getter
@Setter
@RequiredArgsConstructor
public class BookingDto {

    private LocalDateTime start;
    private LocalDateTime end;
    private Long itemId;

    public BookingDto(Long itemId, LocalDateTime star, LocalDateTime end) {
        this.itemId = itemId;
        this.start = star;
        this.end = end;
    }
}

