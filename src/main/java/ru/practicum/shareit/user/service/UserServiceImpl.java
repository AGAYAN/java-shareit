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
        User user = userRepository.save(userMapper.parseUserDtoInUser(userDto));
        return userMapper.parseUserInUserDto(user);
    }

    @Override
    public UserDto updateUser(Long id, @Valid UserDto userDto) {
        ensureUniqueEmail(userDto.getEmail());
        verifyUserId(id);
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Нету такого user под таким id"));

        if (userDto.getName() != null) {
            existingUser.setName(userDto.getName());
        }
        if (userDto.getEmail() != null) {
            existingUser.setEmail(userDto.getEmail());
        }

        User updateUser = userRepository.save(existingUser);

        return userMapper.parseUserInUserDto(updateUser);
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public void deleteUserById(Long id) {
        verifyUserId(id);
        userRepository.deleteById(id);
    }

    private void ensureUniqueEmail(String email) {
        boolean emailExists = userRepository.findByEmail(email).isPresent();

        if (emailExists) {
            throw new IllegalArgumentException("Не удается добавить пользователя с дублирующимся адресом электронной почты");
        }
    }

    @Override
    public void verifyUserId(Long id) {
        if (!userRepository.existsById(id)) {
            throw new NotFoundException("Пользователь с идентификатором = " + id + " не найден");
        }
    }
}