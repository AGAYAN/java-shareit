package ru.practicum.user.dto;

import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@EqualsAndHashCode(exclude = {"id"})
@RequiredArgsConstructor
public class UserDto {
    @Id
    private Long id;

    private String name;

    @Email
    private String email;
}
