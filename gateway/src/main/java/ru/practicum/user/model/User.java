package ru.practicum.user.model;

import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor
@EqualsAndHashCode(exclude = {"id"})
public class User {
    private Long id;

    private String name;

    private String email;

    public User(Long ownerId) {
        this.id = ownerId;
    }
}
