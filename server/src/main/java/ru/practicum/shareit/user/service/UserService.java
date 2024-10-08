package ru.practicum.shareit.user.service;

import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.dto.UserDto;

public interface UserService {
    UserDto addNewUser(UserDto user);

    UserDto updateUser(Long userId, UserDto user);

    User getUserById(Long userId);

    void deleteUserById(Long userId);

    void verifyUserId(Long id);
}
