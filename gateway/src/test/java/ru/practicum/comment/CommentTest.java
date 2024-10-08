package ru.practicum.comment;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.test.context.ContextConfiguration;
import ru.practicum.item.dto.CommentDto;
import ru.practicum.item.model.Item;
import ru.practicum.request.model.ItemRequest;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
@ContextConfiguration(classes = CommentDto.class)
public class CommentTest {

    @Autowired
    public ObjectMapper objectMapper;

    @Test
    void testSerializable() throws JsonProcessingException {
        Item item = new Item();

        item.setId(1L);
        item.setName("test");
        item.setAvailable(true);
        item.setDescription("test description");
        item.setRequest(new ItemRequest());
        LocalDateTime localDateTime = LocalDateTime.of(2004, 4, 23, 12, 4);

        CommentDto commentDto = new CommentDto(1L, "testComment", item, "AGAYAN", localDateTime); // Removed space before '('

        String json = objectMapper.writeValueAsString(commentDto);

        assertThat(json)
                .contains("\"id\":1")
                .contains("\"text\":\"testComment\"")
                .contains("\"authorName\":\"AGAYAN\"")
                .contains("\"created\":\"2004-04-23T12:04:00");
    }

    @Test
    void testDeSerializable() throws JsonProcessingException {
        String json = "{\"id\":1,\"text\":\"testComment\",\"authorName\":\"AGAYAN\",\"created\":\"2004-04-23T12:04:00\"}";

        CommentDto commentDto = objectMapper.readValue(json, CommentDto.class);

        assertThat(commentDto.getId()).isEqualTo(1L);
        assertThat(commentDto.getText()).isEqualTo("testComment");
        assertThat(commentDto.getAuthorName()).isEqualTo("AGAYAN");
        assertThat(commentDto.getCreated()).isEqualTo(LocalDateTime.of(2004, 4, 23, 12, 4));
    }
}