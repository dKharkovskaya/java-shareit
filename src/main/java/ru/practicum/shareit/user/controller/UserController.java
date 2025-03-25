package ru.practicum.shareit.user.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.mapper.mapperUser;
import ru.practicum.shareit.user.service.UserServiceImpl;

import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/users")
@Slf4j
@AllArgsConstructor
public class UserController {
    private UserServiceImpl userServiceImpl;

    @GetMapping
    private Collection<UserDto> getAllUsers() {
        return userServiceImpl.findAll().stream().map(mapperUser::toUserDto).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    private UserDto getUser(@PathVariable("id") int id) {
        try {
            return mapperUser.toUserDto(userServiceImpl.getById(id));
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    private UserDto addUser(@RequestBody UserDto user) {
        var s = userServiceImpl.create(mapperUser.toUser(user));
        return mapperUser.toUserDto(s);
    }

    @PatchMapping("/{id}")
    private UserDto updateUser(@RequestBody UserDto user, @PathVariable int id) {
        try {
            var u = mapperUser.toUser(user);
            userServiceImpl.update(u, id);
            return mapperUser.toUserDto(userServiceImpl.getById(id));
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    private void deleteUser(@PathVariable int id) {
        try {
            userServiceImpl.deleteById(id);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}