package ru.practicum.shareit.booking.service;

import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.enums.BookingStatus;

import java.util.List;

public interface BookingService {
    Booking addBooking(BookingDto booking, Long ownerId);

    Booking updateBooking(Long bookingId, Boolean approved, Long ownerId);

    Booking getBookingById(Long bookingId, Long ownerId);

    List<Booking> getAllBooking();

    List<Booking> getAllBookingByUserId(Long userId, String state);

    List<Booking> getAllUsersItemBookings(Long userId, BookingStatus state);

}
