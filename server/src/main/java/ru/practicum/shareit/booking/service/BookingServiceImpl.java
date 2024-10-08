package ru.practicum.shareit.booking.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.booking.Mapper.BookingMapper;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.enums.BookingStatus;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.comments.mapper.ItemAndCommentDtoMapper;
import ru.practicum.shareit.exeption.NotFoundException;
import ru.practicum.shareit.exeption.ValidationException;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.service.UserService;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final UserService userService;
    private final ItemService itemService;

    @Override
    public Booking addBooking(BookingDto bookingDto, Long ownerId) {

        Booking booking = BookingMapper.parseBookingDtoInBooking(bookingDto);

        userService.verifyUserId(ownerId);
        User user = new User(ownerId);

        Item item = ItemAndCommentDtoMapper
                .parseItemAndCommentDtoInItem(itemService.fetchItemById(bookingDto.getItemId()));

        booking.setBooker(user);
        booking.setItem(item);

        validate(booking);
        booking.setStatus(BookingStatus.WAITING);

        return bookingRepository.save(booking);
    }

    @Override
    public Booking updateBooking(Long bookingId, Boolean approved, Long ownerId) {
        Booking booking = getBookingById(bookingId, ownerId);
        if (!booking.getItem().getOwner().equals(ownerId)) {
            throw new IllegalArgumentException("Пользователь с id = " + ownerId + " не явлется владельцем вещи");
        }
        booking.setStatus(approved ? BookingStatus.APPROVED : BookingStatus.REJECTED);

        return booking;
    }

    @Override
    public Booking getBookingById(Long bookingId, Long ownerId) {
        return bookingRepository.findById(bookingId).orElseThrow(() -> new NotFoundException("Бронировнаие не наейдено"));
    }

    @Override
    public List<Booking> getAllBooking() {
        return bookingRepository.findAll().stream()
                .sorted(Comparator.comparing(Booking::getStart).reversed())
                .toList();
    }

    @Override
    public List<Booking> getAllBookingByUserId(Long userId, String state) {
        List<Booking> userBookings = getAllBooking().stream()
                .filter(booking -> Objects.equals(booking.getBooker().getId(), userId))
                .toList();

        return filterBookingsByState(userBookings, state);
    }

    private List<Booking> filterBookingsByState(List<Booking> bookings, String state) {
        switch (state) {
            case "CURRENT":
            case "FUTURE":
                return bookings.stream()
                        .filter(booking -> booking.getStatus() == BookingStatus.APPROVED)
                        .toList();
            case "PAST":
                return bookings.stream()
                        .filter(booking -> booking.getStatus() == BookingStatus.CANCELED)
                        .toList();
            case "WAITING":
                return bookings.stream()
                        .filter(booking -> booking.getStatus() == BookingStatus.WAITING)
                        .toList();
            case "REJECTED":
                return bookings.stream()
                        .filter(booking -> booking.getStatus() == BookingStatus.REJECTED)
                        .toList();
            default:
                return bookings;
        }
    }

    @Override
    public List<Booking> getAllUsersItemBookings(Long ownerId, BookingStatus state) {
        return getAllBooking().stream()
                .filter(booking -> Objects.equals(booking.getItem().getId(), ownerId))
                .filter(booking -> booking.getStatus() == state)
                .toList();
    }

    private void validate(Booking booking) {
        if (booking.getStart() == null) {
            throw new ValidationException("Start date cannot be null.");
        }
        if (booking.getEnd() == null) {
            throw new ValidationException("End date cannot be null.");
        }
        if (booking.getStart().equals(booking.getEnd())) {
            throw new ValidationException("Start and end dates cannot be equal.");
        }
        if (booking.getEnd().isBefore(booking.getStart())) {
            throw new ValidationException("End date cannot be before start date.");
        }
        if (!itemService.fetchItemById(booking.getItem().getId()).getAvailable()) {
            throw new ValidationException("Item not found or not available.");
        }
    }
}