package ru.practicum.booking;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.test.context.ContextConfiguration;
import ru.practicum.booking.dto.BookingDto;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@JsonTest
@ContextConfiguration(classes = BookingDto.class)
public class BookingTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testSerialize() throws JsonProcessingException {
        ZoneId zone = ZoneId.systemDefault();
        ZonedDateTime now = ZonedDateTime.now(zone);

        BookingDto bookingDto = new BookingDto();
        bookingDto.setId(1L);
        bookingDto.setStart(now.plusDays(5).toLocalDateTime());
        bookingDto.setEnd(now.plusDays(7).toLocalDateTime());

        String json = objectMapper.writeValueAsString(bookingDto);

        assertThat(json)
                .contains("\"id\":1")
                .contains("\"start\":\"")
                .contains("\"end\":\"");
    }

    @Test
    void testDeserialize() throws JsonProcessingException {
        String json = "{\"id\":1,\"start\":\"2024-09-10T11:25:00\",\"end\":\"2024-09-12T00:00:00\"}";
        BookingDto bookingDto = objectMapper.readValue(json, BookingDto.class);

        assertThat(bookingDto.getId()).isEqualTo(1L);
        assertThat(bookingDto.getStart()).isEqualTo(LocalDateTime.of(2024, 9, 10, 11, 25));
        assertThat(bookingDto.getEnd()).isEqualTo(LocalDateTime.of(2024, 9, 12, 0, 0));
    }
}
