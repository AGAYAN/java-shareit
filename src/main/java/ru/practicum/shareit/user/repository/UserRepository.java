package ru.practicum.shareit.user.repository;

import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.dto.UserDto;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    UserDto createUser(User user);

    UserDto modifyUser(Long userId, UserDto user);

    Optional<User> findUserById(Long userId);

    void removeUserById(Long userId);

    List<User> getAllUsers();

}
