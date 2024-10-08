package ru.practicum.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import ru.practicum.user.dto.UserDto;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
class UserTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testSerializable() throws JsonProcessingException {

        UserDto userDto = new UserDto();

        userDto.setId(1L);
        userDto.setName("agayan");
        userDto.setEmail("agayan@mail.ru");

        String json = objectMapper.writeValueAsString(userDto);

        assertThat(json)
                .contains("\"id\":1")
                .contains("\"name\":\"agayan\"")
                .contains("\"email\":\"agayan@mail.ru\"");
    }

    @Test
    void testDeSerializable() throws JsonProcessingException {

        String json = "{\"id\":1,\"name\":\"agayan\",\"email\":\"agayan@mail.ru\"}";

        UserDto userDto = objectMapper.readValue(json, UserDto.class);

        assertThat(userDto.getId()).isEqualTo(1L);
        assertThat(userDto.getName()).isEqualTo("agayan");
        assertThat(userDto.getEmail()).isEqualTo("agayan@mail.ru");
    }
}