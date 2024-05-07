package com.dataincloud.dal.user;

import com.dataincloud.api.configuration.BasicConfiguration;
import com.dataincloud.api.configuration.RepositoryJpaConfiguration;
import com.dataincloud.core.exceptions.ResourceNotFoundException;
import com.dataincloud.core.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Calendar;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {RepositoryJpaConfiguration.class, BasicConfiguration.class})
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;


    private User testingUser;

    @BeforeEach
    void setUp() {
        testingUser = new User();
        testingUser.setUsername("test_user");
        Calendar birthDate = Calendar.getInstance();
        birthDate.set(2003, Calendar.NOVEMBER, 27);
        testingUser.setBirthDate(birthDate);
    }

    @Test
    void createUser() {
        User createdUser = userRepository.create(testingUser);

        assertThat(createdUser)
                .doesNotReturn(null, from(User::getId))
                .returns(testingUser.getUsername(), from(User::getUsername))
                .returns(testingUser.getBirthDate(), from(User::getBirthDate));
    }

    @Test
    void readAllUsers() {
        User createdUser = userRepository.create(testingUser);

        List<User> users = userRepository.readAll();

        assertThat(users)
                .isNotEmpty()
                .contains(createdUser);
    }

    @Test
    void readExistingUserById() {
        User createdUser = userRepository.create(testingUser);

        User readUser = userRepository.readById(createdUser.getId());

        assertThat(readUser)
                .isEqualTo(createdUser);
    }

    @Test
    void readNonExistentUserById() {
        Long nonExistentId = 0L;

        assertThatThrownBy(() -> userRepository.readById(nonExistentId))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void updateExistingUser() {
        User createdUser = userRepository.create(testingUser);
        createdUser.setUsername("test_user_new");

        User updatedUser = userRepository.update(createdUser);

        assertThat(updatedUser)
                .isEqualTo(createdUser);
    }

    @Test
    void deleteExistingUser() {
        User createdUser = userRepository.create(testingUser);

        userRepository.delete(createdUser.getId());

        assertThatThrownBy(() -> userRepository.readById(createdUser.getId()))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void deleteNonExistentUser() {
        Long nonExistentId = 0L;

        assertThatThrownBy(() -> userRepository.delete(nonExistentId))
                .isInstanceOf(ResourceNotFoundException.class);
    }
}