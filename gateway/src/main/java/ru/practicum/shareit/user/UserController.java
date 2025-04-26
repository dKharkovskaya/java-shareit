package ru.practicum.shareit.user;

import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.dto.UserDto;


@RestController
@RequestMapping(path = "/users")
@RequiredArgsConstructor
@Validated
@Slf4j
public class UserController {

    private final UserClient client;

    @GetMapping("/{userId}")
    public ResponseEntity<Object> getUser(@Positive @PathVariable("userId") Long userId) {
        log.info("Get user {}", userId);
        return client.getUser(userId);
    }

    @GetMapping
    public ResponseEntity<Object> getAllUsers() {
        log.info("Get users");
        return client.getAllUsers();
    }

    @PostMapping
    public ResponseEntity<Object> addUser(@Validated @RequestBody UserDto user) {
        log.info("Create user {}", user);
        return client.addUser(user);
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<Object> updateUser(@RequestBody UserDto user,
                                             @Positive @PathVariable("userId") Long userId) {
        return client.updateUser(userId, user);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Object> deleteUser(@Positive @PathVariable("userId") Long userId) {
        log.info("Delete user {}", userId);
        return client.deleteUser(userId);
    }
}