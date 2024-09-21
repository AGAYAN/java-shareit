package ru.practicum.shareit.booking.Mapper;

import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.booking.dto.BookingDto;


public class BookingMapper {

    public static BookingDto parseBookingInBookingDto(Booking booking) {
        return new BookingDto(booking.getBooker().getId(), booking.getStart(), booking.getEnd());
    }

    public static Booking parseBookingDtoInBooking(BookingDto bookingDto) {
        Booking booking = new Booking();

        booking.setStart(bookingDto.getStart());
        booking.setEnd(bookingDto.getEnd());

        return booking;
    }
}
