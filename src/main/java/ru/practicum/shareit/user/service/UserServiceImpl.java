package ru.practicum.shareit.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.error.exception.ConflictException;
import ru.practicum.shareit.error.exception.NotFoundException;
import ru.practicum.shareit.error.exception.ValidationException;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public Collection<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User create(User user) {
        validate(user);
        validateEmail(user);
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public User update(User user, Long id) {
        user.setId(id);
        validateEmail(user);
        User oldUser = userRepository.findById(user.getId()).orElseThrow(() -> new NotFoundException("Not found user"));
        if (user.getName() != null) {
            oldUser.setName(user.getName());
        }
        if (user.getEmail() != null) {
            oldUser.setEmail(user.getEmail());
        }
        return userRepository.save(oldUser);
    }

    @Override
    @Transactional
    public User getById(Long id) {
        return userRepository.getById(id);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("Not found user"));
        userRepository.delete(user);
    }

    private void validate(User user) {
        if (user.getEmail() == null || !(user.getEmail().contains("@"))) {
            throw new ValidationException("The user email must include @, should be without spaces " +
                    "and shouldn't be blank");
        }
        if (user.getName() == null || user.getName().equals("")) {
            throw new NotFoundException("The user name can't be empty or contains spaces");
        }
    }

    private void validateEmail(User user) {
        for (User u : userRepository.findAll()) {
            if (u.getId() == user.getId()) {
                continue;
            }
            if (u.getEmail().equals(user.getEmail())) {
                throw new ConflictException("The user email is already exist");
            }
        }
    }
}
