package ru.practicum.shareit.user.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.user.model.User;

import java.util.*;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private Map<Integer, User> users = new HashMap<>();

    @Override
    public Collection<User> findAll() {
        return users.values();
    }

    @Override
    public User create(User user) {
        validate(user);
        user.setId(getNextId());
        users.put(user.getId(), user);
        log.info("user {} has been added", users.toString().toUpperCase());
        return user;
    }

    @Override
    public User update(User user, Integer id) {
        user.setId(id);
        validateEmail(user);
        User oldUser = users.get(user.getId());
        if (user.getName() != null) {
            oldUser.setName(user.getName());
        }
        if (user.getEmail() != null) {
            oldUser.setEmail(user.getEmail());
        }
        return oldUser;
    }

    @Override
    public User getById(int id) {
        if (!users.containsKey(id)) {
            throw new ValidationException(HttpStatus.NOT_FOUND, "The user just not exist");
        }
        return users.get(id);
    }

    @Override
    public Boolean deleteById(int id) {
        if (users.containsKey(id)) {
            users.remove(id);
            return true;
        } else {
            return false;
        }
    }

    private void validate(User user) {
        if (user.getEmail() == null || !(user.getEmail().contains("@"))) {
            throw new ValidationException(HttpStatus.BAD_REQUEST, "The user email must include @, should be without spaces " +
                    "and shouldn't be blank");
        }
        if (user.getName() == null || user.getName().equals("")) {
            throw new ValidationException(HttpStatus.BAD_REQUEST, "The user name can't be empty or contains spaces");
        }
        for (User u : users.values()) {
            if (u.getId() == user.getId()) {
                continue;
            }
            if (u.getEmail().equals(user.getEmail())) {
                throw new ValidationException(HttpStatus.CONFLICT, "The user email is already exist");
            }
        }
    }

    private void validateEmail(User user) {
        for (User u : users.values()) {
            if (u.getId() == user.getId()) {
                continue;
            }
            if (u.getEmail().equals(user.getEmail())) {
                throw new ValidationException(HttpStatus.CONFLICT, "The user email is already exist");
            }
        }
    }

    // вспомогательный метод для генерации идентификатора нового поста
    private int getNextId() {
        int currentMaxId = users.keySet().stream().mapToInt(id -> id).max().orElse(0);
        return ++currentMaxId;
    }

}
