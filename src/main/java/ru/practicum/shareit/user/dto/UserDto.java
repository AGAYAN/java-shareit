package ru.practicum.shareit.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.practicum.shareit.user.User;

@Data
@EqualsAndHashCode(exclude = {"id"})
public class UserDto extends User {
    private Long id;

    @NotBlank(message = "Name cannot be null or empty")
    private String name;

    @NotBlank(message = "Email cannot be null or empty")
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@(.+)$", message = "Invalid email format")
    private String email;
}
