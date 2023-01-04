package com.example.swimmingchampionship.service;

import com.example.swimmingchampionship.dto.UserRequest;
import com.example.swimmingchampionship.exception.UserIdNotFoundException;
import com.example.swimmingchampionship.mapper.UserMapper;
import com.example.swimmingchampionship.model.User;
import com.example.swimmingchampionship.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Test
    @DisplayName("Get user by id happy path")
    void getUserByIdTest() {
        int id = 1;
        User user = new User();
        user.setId(id);
        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        User result = userService.getUserById(id);

        assertNotNull(result);
        assertEquals(user.getId(), result.getId());

        verify(userRepository).findById(id);
    }

    @Test
    @DisplayName("Get user by id not found")
    void getUserByIdExceptionTest(){
        int id = 1;
        when(userRepository.findById(id)).thenReturn(Optional.empty());

        UserIdNotFoundException exception = assertThrows(UserIdNotFoundException.class,
                () -> userService.getUserById(id));

        assertNotNull(exception);
        assertEquals("User with id 1 does not exist!", exception.getMessage());

        verify(userRepository).findById(id);
    }

    @Test
    @DisplayName("Save user")
    void saveUserTest(){
        UserRequest userRequest = new UserRequest("User", "user@gmail.com");
        User user = new User("User", "user@gmail.com");
        User savedUser = new User("User", "user@gmail.com");
        when(userMapper.userRequestToUser(userRequest)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(savedUser);

        User result = userService.saveUser(userRequest);

        assertNotNull(result);
        assertEquals(userRequest.getUsername(), result.getUsername());
        assertEquals(userRequest.getEmail(), result.getEmail());

        verify(userRepository).save(user);
    }

}