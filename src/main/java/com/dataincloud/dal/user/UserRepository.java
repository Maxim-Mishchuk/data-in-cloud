package com.dataincloud.dal.user;

import com.dataincloud.core.user.IUserRepository;
import com.dataincloud.core.user.User;
import com.dataincloud.core.exceptions.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
@RequiredArgsConstructor
public class UserRepository implements IUserRepository {
    private final UserJpaRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public User create(User newUser) {
        return modelMapper.map(
            userRepository.save(modelMapper.map(newUser, UserJpa.class)), User.class
        );
    }

    @Override
    public List<User> readAll() {
        return userRepository.findAll().stream()
                .peek(userJpa -> userJpa.setPosts(null))
                .map(userJpa -> modelMapper.map(userJpa, User.class))
                .toList();
    }

    @Override
    public User readById(Long id) {
        return modelMapper.map(
                userRepository.findByIdFetch(id).orElseThrow(() -> new ResourceNotFoundException("User not found")), User.class
        );
    }

    @Override
    public User update(User editedUser) {
        return modelMapper.map(
                userRepository.save(modelMapper.map(editedUser, UserJpa.class)), User.class
        );
    }

    @Override
    public User delete(Long id) {
        User deletedUser = modelMapper.map(
                userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found")), User.class
        );
        userRepository.deleteById(id);
        return deletedUser;
    }
}
