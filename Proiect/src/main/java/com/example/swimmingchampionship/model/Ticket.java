package com.example.swimmingchampionship.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@ApiModel(value = "Ticket details", description = "Full information stored for a ticket")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(notes = "The id of the ticket", required = true, example = "1")
    private int id;

    @NotNull(message = "Quantity must have a value!")
    @Min(value = 1, message = "Minimum quantity is 1!")
    @ApiModelProperty(notes = "The number of people who are allowed to enter based on the ticket", required = true, example = "3")
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "session_id")
    @NotNull(message = "Ticket session must have a value!")
    @ApiModelProperty(notes = "The session the ticket was bought for")
    private Session session;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @NotNull(message = "Ticket holder must have a value!")
    @JsonIgnore
    @ApiModelProperty(notes = "The user who bought the ticket")
    private User user;

    public Ticket() {
    }

    public Ticket(int quantity, Session session, User user) {
        this.quantity = quantity;
        this.session = session;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
