package ru.practicum.booking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.BaseClient;
import ru.practicum.booking.dto.BookingDto;
import ru.practicum.booking.model.BookingStatus;

import java.util.Map;

@Service
public class BookingClient extends BaseClient {

    private static final String API_PREFIX = "/bookings";

    @Autowired
    public BookingClient(@Value("${shareit-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(builder
                .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                .requestFactory(() -> new HttpComponentsClientHttpRequestFactory())
                .build()
        );
    }

    public ResponseEntity<Object> createBooking(Long userId, BookingDto bookingDto) {
        return post("", userId, bookingDto);
    }

    public ResponseEntity<Object> getBookingById(Long bookingId, Long userId) {
        return get("/" + bookingId, userId);
    }

    public ResponseEntity<Object> getAllBookingByUserId(Long userId, BookingStatus bookingStatus) {
        return get("", userId, Map.of("state", bookingStatus));
    }

    public ResponseEntity<Object> getAllUsersItemBookings(Long userId, BookingStatus bookingStatus) {
        return get("/owner", userId, Map.of("state", bookingStatus));
    }

    public ResponseEntity<Object> update(Long bookingId, Boolean approved, Long userId) {
        return patch("/" + bookingId + "?approved={approved}",
                userId,
                Map.of("approved", approved),
                null);
    }

}
