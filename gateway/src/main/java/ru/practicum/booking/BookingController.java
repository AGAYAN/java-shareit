package ru.practicum.booking;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.booking.dto.BookingDto;
import ru.practicum.booking.model.BookingStatus;

@RestController
@RequestMapping(path = "/bookings")
@RequiredArgsConstructor
public class BookingController {
    private final BookingClient bookingClient;
    private static final String USER_ID_HEADER = "X-Sharer-User-Id";

    @PostMapping
    public ResponseEntity<Object> createBooking(@RequestBody BookingDto bookingDto,
                                                @RequestHeader(USER_ID_HEADER) Long userId) {
        return bookingClient.createBooking(userId, bookingDto);
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<Object> getBookingById(@PathVariable("bookingId") Long bookingId,
                                                 @RequestHeader(USER_ID_HEADER) Long userId) {
        return bookingClient.getBookingById(bookingId, userId);
    }

    @GetMapping
    public ResponseEntity<Object> getUsersBooking(@RequestHeader(USER_ID_HEADER) Long userId,
                                         @RequestParam(name = "state",
                                                 required = false,
                                                 defaultValue = "ALL") BookingStatus state) {
        return bookingClient.getAllBookingByUserId(userId, state);
    }

    @GetMapping("/owner")
    public ResponseEntity<Object> getAllUsersItemBookings(@RequestHeader(USER_ID_HEADER) Long userId,
                                                 @RequestParam(name = "state",
                                                         required = false,
                                                         defaultValue = "ALL") BookingStatus state) {
        return bookingClient.getAllUsersItemBookings(userId, state);
    }

    @PatchMapping("/{bookingId}")
    public ResponseEntity<Object> update(@PathVariable("bookingId") Long bookingId,
                                         @RequestParam(name = "approved") Boolean approved,
                                         @RequestHeader(USER_ID_HEADER) Long userId) {
        return bookingClient.update(bookingId, approved, userId);
    }
}
