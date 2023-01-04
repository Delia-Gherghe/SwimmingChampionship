package com.example.swimmingchampionship.mapper;

import com.example.swimmingchampionship.dto.UserRequest;
import com.example.swimmingchampionship.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public User userRequestToUser(UserRequest userRequest){
        return new User(userRequest.getUsername(), userRequest.getEmail());
    }
}
