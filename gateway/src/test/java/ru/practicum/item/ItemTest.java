package ru.practicum.item;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.test.context.ContextConfiguration;
import ru.practicum.item.dto.ItemDto;
import static org.assertj.core.api.Assertions.assertThat;


@JsonTest
@ContextConfiguration(classes = {ItemDto.class})
public class ItemTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testSerializable() throws Exception {
        ItemDto itemDto = new ItemDto();
        itemDto.setId(1L);
        itemDto.setName("test");
        itemDto.setDescription("testing description");
        itemDto.setAvailable(true);
        itemDto.setRequestId(1L);
        String json = objectMapper.writeValueAsString(itemDto);

        assertThat(json)
                .contains("\"id\":1")
                .contains("\"name\":\"test\"")
                .contains("\"description\":\"testing description\"")
                .contains("\"available\":true")
                .contains("\"requestId\":1");
    }

    @Test
    void testItemDtoDeSerializableEqualsRequestId() throws JsonProcessingException {
        String json = "{\"id\":1,\"name\":\"test\",\"description\":\"testing description\",\"available\":true,\"requestId\":1}";

        ItemDto itemDto = objectMapper.readValue(json, ItemDto.class);

        assertThat(itemDto.getId()).isEqualTo(1L);
        assertThat(itemDto.getName()).isEqualTo("test");
        assertThat(itemDto.getDescription()).isEqualTo("testing description");
        assertThat(itemDto.getAvailable()).isEqualTo(true);
        assertThat(itemDto.getRequestId()).isEqualTo(1L);
    }
}
