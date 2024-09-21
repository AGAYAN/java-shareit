package ru.practicum.shareit.user;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.service.UserService;

import java.util.Optional;


@RestController
@RequestMapping(path = "/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @PostMapping
    public UserDto addNewUser(@Valid @RequestBody UserDto user) {
        log.info("Проходит добавление");
        return userService.addNewUser(user);
    }

    @PatchMapping("/{userId}")
    public UserDto updateUser(@PathVariable("userId") Long userId,
                              @RequestBody UserDto user) {
        log.info("Проходит изменение");
        return userService.updateUser(userId, user);
    }

    @GetMapping("/{userId}")
    public Optional<User> getUserBuId(@PathVariable("userId") Long userId) {
        log.info("Поиск user по id:{}", userId);
        return userService.getUserById(userId);
    }

    @DeleteMapping("/{userId}")
    public void deleteUserBuID(@Positive @PathVariable("userId") Long userId) {
        log.info("Происходит удаление по id:{}", userId);
        userService.deleteUserById(userId);
    }

}