package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.enums.BookingStatus;
import ru.practicum.shareit.booking.service.BookingService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/bookings")
@RequiredArgsConstructor
public class BookingController {
    private static final String USER_ID_HEADER = "X-Sharer-User-Id";
    private final BookingService bookingService;

    @PostMapping
    public Booking createNewBooking(@RequestBody BookingDto booking,
                                    @RequestHeader(USER_ID_HEADER) Long userId) {
        log.info("Созание пользователя");
        return bookingService.addBooking(booking, userId);
    }

    @GetMapping("/{bookingId}")
    public Booking getBookingByID(@PathVariable("bookingId") Long bookingId,
                                  @RequestHeader(USER_ID_HEADER) Long userId) {
        log.info("Вывод booking {bookingId}" + bookingId);
        return bookingService.getBookingById(bookingId, userId);
    }

    @GetMapping
    public List<Booking> getUsersBooking(@RequestHeader(USER_ID_HEADER) Long userId,
                                         @RequestParam(name = "state",
                                                 required = false,
                                                 defaultValue = "ALL") String state) {
        log.info("вывод user {userId}" + userId);
        return bookingService.getAllBookingByUserId(userId, state);
    }

    @GetMapping("/owner")
    public List<Booking> getAllUsersItemBookings(@RequestHeader(USER_ID_HEADER) Long userId,
                                                 @RequestParam(name = "state",
                                                         required = false,
                                                         defaultValue = "ALL") BookingStatus state) {
        log.info("вывод всех user из item");
        return bookingService.getAllUsersItemBookings(userId, state);
    }

    @PatchMapping("/{bookingId}")
    public Booking update(@PathVariable("bookingId") Long bookingId,
                          @RequestParam(name = "approved") Boolean approved,
                          @RequestHeader(USER_ID_HEADER) Long userId) {
        log.info("Обновление booking");
        return bookingService.updateBooking(bookingId, approved, userId);
    }
}
