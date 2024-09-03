package ru.practicum.shareit.user.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exeption.NotFoundException;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserDto addNewUser(@Valid UserDto userDto) {
        ensureUniqueEmail(userDto.getEmail());
        return userRepository.createUser(userMapper.parseUserDtoInUser(userDto));
    }

    @Override
    public UserDto updateUser(Long id, @Valid UserDto userDto) {
        ensureUniqueEmail(userDto.getEmail());
        verifyUserId(id);
        return userRepository.updateUser(id, userDto);
    }

    @Override
    public UserDto getUserById(Long id) {
        verifyUserId(id);
        Optional<User> userOptional = userRepository.findUserById(id);
        User user = userOptional.orElseThrow(() -> new NotFoundException("Пользователь не найден"));
        return userMapper.parseUserInUserDto(user);
    }

    @Override
    public void deleteUserById(Long id) {
        verifyUserId(id);
        userRepository.removeUserById(id);
    }

    private void ensureUniqueEmail(String email) {
        boolean emailExists = userRepository.getAllUsers().stream()
                .anyMatch(existingUser -> existingUser.getEmail().equals(email));

        if (emailExists) {
            throw new IllegalArgumentException("Не удается добавить пользователя с дублирующимся адресом электронной почты");
        }
    }

    private void verifyUserId(Long id) {
        if (!userRepository.isExists(id)) {
            throw new NotFoundException("Пользователь с идентификатором = " + id + " не найден");
        }
    }
}