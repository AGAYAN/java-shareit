package ru.practicum.shareit.user.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.practicum.shareit.user.User;

@Data
@EqualsAndHashCode(exclude = {"id"})
public class UserDto extends User {
    private Long id;

    private String name;

    private String email;
}
