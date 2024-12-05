package com.dataincloud.api.controllers.user;

import com.dataincloud.services.user.UserService;
import com.dataincloud.services.user.dto.BasicUserDto;
import com.dataincloud.services.user.dto.UserCreateDto;
import com.dataincloud.services.user.dto.UserDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto create(@RequestBody @Valid UserCreateDto newUser) {
        return userService.create(newUser);
    }

    @GetMapping
    public List<BasicUserDto> getAll() {
        return userService.getAll();
    }

    @GetMapping("/{id}")
    public UserDto getById(@PathVariable Long id) {
        return userService.getById(id);
    }

    @PutMapping
    public UserDto update(@Valid @RequestBody UserDto editedUser) {
        return userService.update(editedUser);
    }

    @DeleteMapping("/{id}")
    public UserDto delete(@PathVariable Long id) {
        return userService.deleteById(id);
    }
}
