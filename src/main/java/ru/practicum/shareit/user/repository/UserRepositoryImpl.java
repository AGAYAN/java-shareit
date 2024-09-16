package ru.practicum.shareit.user.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.dto.UserDto;

import java.util.*;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final Map<Long, User> users = new HashMap<>();
    private final UserMapper userMapper;
    private long currentId = 0;


    @Override
    public UserDto createUser(User user) {
        user.setId(generateNextId());
        users.put(user.getId(), user);
        return userMapper.parseUserInUserDto(user);
    }

    @Override
    public UserDto updateUser(Long id, UserDto userDto) {
        User existingUser = users.get(id);
        validate(userDto, existingUser);
        users.put(id, existingUser);
        return userMapper.parseUserInUserDto(existingUser);
    }

    @Override
    public Optional<User> findUserById(Long id) {
        return Optional.ofNullable(users.get(id));
    }

    @Override
    public void removeUserById(Long id) {
        users.remove(id);
    }

    @Override
    public List<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }

    @Override
    public boolean isExists(Long id) {
        return users.containsKey(id);
    }

    private Long generateNextId() {
        return ++currentId;
    }

    public void validate(UserDto userDto, User existingUser) {
        if (userDto.getName() != null) {
            existingUser.setName(userDto.getName());
        }

        if (userDto.getEmail() != null) {
            existingUser.setEmail(userDto.getEmail());
        }

    }
}