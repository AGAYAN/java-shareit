package ru.practicum.booking.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookingDto {

    private Long id;

    @FutureOrPresent
    private LocalDateTime start;

    @Future
    private LocalDateTime end;

    private Long itemId;
}