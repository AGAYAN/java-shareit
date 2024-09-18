package ru.practicum.shareit.booking.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@RequiredArgsConstructor
public class BookingDto {

    @NotNull(message = "Start date cannot be null.")
    @Future(message = "Start date must be in the future.")
    private LocalDateTime start;

    @NotNull(message = "End date cannot be null.")
    @Future(message = "End date must be in the future.")
    private LocalDateTime end;

    @NotNull(message = "Item cannot be null.")
    private Long itemId;

    public BookingDto(Long itemId, LocalDateTime star, LocalDateTime end) {
        this.itemId = itemId;
        this.start = star;
        this.end = end;
    }
}

