package ru.practicum.shareit.user.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.mapper.MapperUser;
import ru.practicum.shareit.user.service.UserService;

import java.util.Collection;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/users")
@Slf4j
@AllArgsConstructor
public class UserController {
    private UserService userService;

    @GetMapping
    private Collection<UserDto> getAllUsers() {
        return userService.findAll().stream().map(MapperUser::toUserDto).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    private UserDto getUser(@PathVariable("id") Long id) {
            return MapperUser.toUserDto(userService.getById(id));
    }

    @PostMapping
    private UserDto addUser(@RequestBody UserDto user) {
        var s = userService.create(MapperUser.toUser(user));
        return MapperUser.toUserDto(s);
    }

    @PatchMapping("/{id}")
    private UserDto updateUser(@RequestBody UserDto user, @PathVariable Long id) {
            var u = MapperUser.toUser(user);
            userService.update(u, id);
            return MapperUser.toUserDto(userService.getById(id));
    }

    @DeleteMapping("/{id}")
    private void deleteUser(@PathVariable Long id) {
            userService.deleteById(id);
    }
}