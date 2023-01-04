package com.example.swimmingchampionship.controller;

import com.example.swimmingchampionship.dto.UserRequest;
import com.example.swimmingchampionship.model.User;
import com.example.swimmingchampionship.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @Test
    @DisplayName("Get user by id")
    void getUserTicketsTest() throws Exception {
        int id = 1;
        User user = new User();
        user.setId(id);
        when(userService.getUserById(id)).thenReturn(user);
        String url = "/user/{userId}/tickets";

        mockMvc.perform(get(url, id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(user.getId()));
    }

    @Test
    @DisplayName("Add user happy path")
    void addUserTest() throws Exception {
        UserRequest userRequest = new UserRequest("Username", "user@gmail.com");
        User user = new User("Username", "user@gmail.com");
        when(userService.saveUser(any())).thenReturn(user);

        mockMvc.perform(post("/user").contentType("application/json").content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value(userRequest.getUsername()))
                .andExpect(jsonPath("$.email").value(userRequest.getEmail()));
    }

    @Test
    @DisplayName("Add user invalid body")
    void addUserExceptionTest() throws Exception {
        UserRequest userRequest = new UserRequest("Username", "usergmail.com");

        MvcResult requestResult = mockMvc.perform(post("/user").contentType("application/json").content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isBadRequest()).andReturn();

        String result = requestResult.getResponse().getContentAsString();
        assertEquals("Email address must be valid!", result);
    }
}