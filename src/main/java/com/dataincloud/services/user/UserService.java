package com.dataincloud.services.user;

import com.dataincloud.core.user.IUserRepository;
import com.dataincloud.core.user.User;
import com.dataincloud.services.user.dto.BasicUserDto;
import com.dataincloud.services.user.dto.UserCreateDto;
import com.dataincloud.services.user.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;

import java.util.List;

@RequiredArgsConstructor
public class UserService {
    private final IUserRepository userRepository;
    private final ModelMapper modelMapper;

    public UserDto create(UserCreateDto newUser) {
        return modelMapper.map(
            userRepository.create(modelMapper.map(newUser, User.class)), UserDto.class
        );
    }

    public List<BasicUserDto> getAll() {
        return userRepository.readAll().stream()
                .map(user -> modelMapper.map(user, BasicUserDto.class))
                .toList();
    }

    public UserDto getById(Long id) {
        return modelMapper.map(
                userRepository.readById(id), UserDto.class
        );
    }

    public UserDto update(UserDto editedUser) {
        return modelMapper.map(
                userRepository.update(modelMapper.map(editedUser, User.class)), UserDto.class
        );
    }

    public UserDto deleteById(Long id) {
        return modelMapper.map(
                userRepository.delete(id), UserDto.class
        );
    }
}