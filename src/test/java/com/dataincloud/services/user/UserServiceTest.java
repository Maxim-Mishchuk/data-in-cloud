package com.dataincloud.services.user;

import com.dataincloud.api.configuration.BasicConfiguration;
import com.dataincloud.core.user.User;
import com.dataincloud.dal.user.UserRepository;
import com.dataincloud.services.user.dto.UserCreateDto;
import com.dataincloud.services.user.dto.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Calendar;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class, SpringExtension.class})
@ContextConfiguration(classes = BasicConfiguration.class)
class UserServiceTest {
    private UserService userService;
    @Mock
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        userService = new UserService(userRepository, modelMapper);

    }

    @Test
    void canCreateUser() {
        UserCreateDto newUserDto = new UserCreateDto();
        newUserDto.setUsername("new_user");
        Calendar birthDate = Calendar.getInstance();
        birthDate.set(2003, Calendar.NOVEMBER, 27);
        newUserDto.setBirthDate(birthDate);

        User newUser = modelMapper.map(newUserDto, User.class);

        when(userRepository.create(newUser)).thenAnswer(invocation -> {
            User createdUser = invocation.getArgument(0);
            createdUser.setId(1L);
            return createdUser;
        });

        UserDto createdUser = userService.create(newUserDto);

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository)
                .create(userCaptor.capture());

        assertThat(createdUser)
                .returns(newUserDto.getUsername(), from(UserDto::getUsername))
                .returns(newUserDto.getBirthDate(), from(UserDto::getBirthDate));
    }

    @Test
    void canGetAllUsers() {
        userService.getAll();

        verify(userRepository).readAll();
    }

    @Test
    void canGetUserById() {
        Long id = 1L;

        when(userRepository.readById(id)).thenAnswer(invocation -> {
           User returnedUser = new User();
           returnedUser.setId(id);
           return returnedUser;
        });

        UserDto returnedUser = userService.getById(id);

        ArgumentCaptor<Long> idCaptor = ArgumentCaptor.forClass(Long.class);
        verify(userRepository).readById(idCaptor.capture());

        assertThat(returnedUser)
                .returns(idCaptor.getValue(), from(UserDto::getId));
    }

    @Test
    void canUpdateUser() {
        UserDto editedUserDto = new UserDto();
        editedUserDto.setId(1L);
        editedUserDto.setUsername("edited_user");
        Calendar birthDate = Calendar.getInstance();
        birthDate.set(2003, Calendar.NOVEMBER, 27);
        editedUserDto.setBirthDate(birthDate);

        User editedUser = modelMapper.map(editedUserDto, User.class);

        when(userRepository.update(editedUser)).thenAnswer(invocation -> invocation.getArgument(0));

        userService.update(editedUserDto);

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).update(userCaptor.capture());
    }

    @Test
    void canDeleteUserById() {
        Long id = 1L;

        when(userRepository.delete(id)).thenReturn(new User());

        userService.deleteById(id);

        verify(userRepository).delete(id);
    }
}