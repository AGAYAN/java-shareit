package ru.practicum.shareit.user.service;

import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.dto.UserDto;

import java.util.Optional;

public interface UserService {
    UserDto addNewUser(UserDto user);

    UserDto updateUser(Long userId, UserDto user);

    Optional<User> getUserById(Long userId);

    void deleteUserById(Long userId);

    void verifyUserId(Long id);
}
