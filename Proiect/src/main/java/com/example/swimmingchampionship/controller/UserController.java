package com.example.swimmingchampionship.controller;

import com.example.swimmingchampionship.dto.UserRequest;
import com.example.swimmingchampionship.model.User;
import com.example.swimmingchampionship.service.UserService;
import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
@Api(value = "/user", tags = "Users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{userId}/tickets")
    @ApiOperation(value = "Show user", notes = "Displays all the information about a user and their tickets based on the given id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The user was successfully retrieved"),
            @ApiResponse(code = 404, message = "User with given id not found")
    })
    public ResponseEntity<User> getUserTickets(@PathVariable @ApiParam(value = "The id of the user", required = true) int userId){
        return ResponseEntity.ok().body(userService.getUserById(userId));
    }

    @PostMapping
    @ApiOperation(value = "Create a user", notes = "Creates a new User based on the information received in the request")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "The User was successfully created based on the received request"),
            @ApiResponse(code = 400, message = "Validation error on the received request")
    })
    public ResponseEntity<User> addUser(@RequestBody @Valid UserRequest userRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.saveUser(userRequest));
    }
}
