package com.example.swimmingchampionship.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@ApiModel(value = "User request", description = "The details needed to add a new user")
public class UserRequest {
    @NotBlank(message = "Username can't be empty or null")
    @ApiModelProperty(notes = "The username of the user", required = true, example = "Maria_Ionescu")
    private String username;

    @NotNull(message = "Email address can't be null!")
    @Email(message = "Email address must be valid!")
    @ApiModelProperty(notes = "The email address of the user", required = true, example = "maria.ionescu@gmail.com")
    private String email;

    public UserRequest(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
