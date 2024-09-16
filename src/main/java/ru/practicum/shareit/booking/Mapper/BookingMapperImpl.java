package ru.practicum.shareit.booking.Mapper;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.booking.dto.BookingDto;

@Component
public class BookingMapperImpl {

    public BookingDto parseBookingInBookingDto(Booking booking) {
        return new BookingDto(booking.getBooker().getId(), booking.getStart(), booking.getEnd());
    }

    public Booking parseBookingDtoInBooking(BookingDto bookingDto) {
        Booking booking = new Booking();

        booking.setStart(bookingDto.getStart());
        booking.setEnd(bookingDto.getEnd());

        return booking;
    }
}
