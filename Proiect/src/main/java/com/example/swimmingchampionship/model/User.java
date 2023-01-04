package com.example.swimmingchampionship.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@ApiModel(value = "User details", description = "Full information stored for a user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(notes = "The id of the user", required = true, example = "1")
    private int id;

    @NotBlank(message = "Username can't be empty or null")
    @ApiModelProperty(notes = "The username of the user", required = true, example = "Maria_Ionescu")
    private String username;

    @NotNull(message = "Email address can't be null!")
    @Email(message = "Email address must be valid!")
    @ApiModelProperty(notes = "The email address of the user", required = true, example = "maria.ionescu@gmail.com")
    private String email;

    @OneToMany(mappedBy = "user")
    @ApiModelProperty(notes = "The tickets the user holds")
    private List<Ticket> ticketList;

    public User() {
    }

    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public List<Ticket> getTicketList() {
        return ticketList;
    }

    public void setTicketList(List<Ticket> ticketList) {
        this.ticketList = ticketList;
    }
}
