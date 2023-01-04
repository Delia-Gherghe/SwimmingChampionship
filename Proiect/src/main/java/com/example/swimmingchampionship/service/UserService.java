package com.example.swimmingchampionship.service;

import com.example.swimmingchampionship.dto.UserRequest;
import com.example.swimmingchampionship.exception.UserIdNotFoundException;
import com.example.swimmingchampionship.mapper.UserMapper;
import com.example.swimmingchampionship.model.User;
import com.example.swimmingchampionship.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public User getUserById(int userId){
        return userRepository.findById(userId).orElseThrow(() ->
                new UserIdNotFoundException("User with id " + userId + " does not exist!"));
    }

    public User saveUser(UserRequest userRequest){
        return userRepository.save(userMapper.userRequestToUser(userRequest));
    }
}
