package ru.practicum.request;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import ru.practicum.request.dto.ItemRequestDto;
import ru.practicum.request.dto.ItemResponsDtoOwner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class RequestTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testSerializable() throws JsonProcessingException {
        ItemRequestDto itemRequestDto = new ItemRequestDto();
        itemRequestDto.setId(1L);
        LocalDateTime now = LocalDateTime.of(2004, 4, 15, 2, 42);
        itemRequestDto.setCreated(now);
        itemRequestDto.setDescription("Test description");
        List<ItemResponsDtoOwner> items = new ArrayList<>();

        ItemResponsDtoOwner item = new ItemResponsDtoOwner();
        item.setId(1L);
        item.setName("Test Item");
        item.setOwnerId(100L);

        items.add(item);
        itemRequestDto.setItems(items);

        String json = objectMapper.writeValueAsString(itemRequestDto);

        assertThat(json)
                .contains("\"id\":1")
                .contains("\"description\":\"Test description\"")
                .contains("\"created\":\"2004-04-15T02:42:00\"")
                .contains("\"items\":[{\"id\":1,\"name\":\"Test Item\",\"ownerId\":100}]");
    }


    @Test
    void testDeSerializable() throws JsonProcessingException {
        String json = "{\"id\":1,\"description\":\"Test description\",\"created\":\"2004-04-15T02:42:00\",\"items\":[{\"id\":1,\"name\":\"Test Item\",\"ownerId\":100}]}";

        ItemRequestDto itemRequestDto = objectMapper.readValue(json, ItemRequestDto.class);

        assertThat(itemRequestDto.getId()).isEqualTo(1L);
        assertThat(itemRequestDto.getDescription()).isEqualTo("Test description");
        assertThat(itemRequestDto.getCreated()).isEqualTo(LocalDateTime.of(2004, 4, 15, 2, 42));
        assertThat(itemRequestDto.getItems());
    }
}