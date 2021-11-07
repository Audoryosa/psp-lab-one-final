package com.audriuskumpis.labOneThird.service;

import com.audriuskumpis.labOneThird.exception.BadEmailException;
import com.audriuskumpis.labOneThird.exception.BadPasswordException;
import com.audriuskumpis.labOneThird.exception.BadPhoneNumberException;
import com.audriuskumpis.labOneThird.model.User;
import com.audriuskumpis.labOneThird.myValidators.MyEmailValidator;
import com.audriuskumpis.labOneThird.myValidators.MyPasswordValidator;
import com.audriuskumpis.labOneThird.myValidators.MyPhoneNumberValidator;
import com.audriuskumpis.labOneThird.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final MyPhoneNumberValidator numberValidator;
    private final MyPasswordValidator passwordValidator;
    private final MyEmailValidator emailValidator;
    private final UserRepository repository;

    public User save(User user) {
        boolean isUserValid = validateUser(user);

        if (isUserValid) {
            return repository.save(user);
        } else {
            log.error("User {} has incorrect fields.", user);
            throw new IllegalArgumentException("user:[" + user + "] has incorrect fields.");
        }
    }

    public User update(int id, User newUser) {
        boolean isNewUserValid = validateUser(newUser);
        if (!isNewUserValid) {
            throw new IllegalArgumentException("New user has incorrect fields.");
        }
        User user = repository.findById(id).orElseThrow();
        updateUser(user, newUser);

        return repository.save(user);
    }

    public void deleteById(int id) {
        repository.deleteById(id);
        log.info("Deleted user by id={}", id);
    }

    public List<User> findAll() {
        return repository.findAll();
    }

    public User findById(int id) {
        if (id < 1) {
            throw new IllegalArgumentException("ID cannot be negative");
        }
        return repository.findById(id).orElseThrow();
    }

    private void updateUser(User user, User newUser) {
        user.setAddress(newUser.getAddress());
        user.setEmail(newUser.getEmail());
        user.setName(newUser.getName());
        user.setPassword(newUser.getPassword());
        user.setSurname(newUser.getSurname());
        user.setPhoneNumber(newUser.getPhoneNumber());
    }

    private boolean validateUser(User user) {
        boolean isNumberValid = numberValidator.validate(user.getPhoneNumber());
        if (!isNumberValid) {
            log.error("Phone number [{}] is not valid.", user.getPhoneNumber());
            throw new BadPhoneNumberException("Phone number is not valid! " + user.getPhoneNumber());
        }

        boolean isPasswordValid = passwordValidator.validate(user.getPassword());
        if (!isPasswordValid) {
            log.error("Password is not valid.");
            throw new BadPasswordException("Password is not valid!");
        }

        boolean isEmailValid = emailValidator.validate(user.getEmail());
        if (!isEmailValid) {
            log.error("Email[{}] is not valid.", user.getEmail());
            throw new BadEmailException("Email is not valid!");
        }
        return true;
    }
}
