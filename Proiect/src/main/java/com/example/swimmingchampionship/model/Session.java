package com.example.swimmingchampionship.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@ApiModel(value = "Session details", description = "Full information stored for a session")
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(notes = "The id of the session", required = true, example = "1")
    private int id;

    @NotNull(message = "Session must have a date and time!")
    @ApiModelProperty(notes = "The date and start time of the session", required = true)
    private LocalDateTime dateTime;

    @Min(value = 10, message = "Session price must be at least 10$!")
    @ApiModelProperty(notes = "The price of the tickets for the session", required = true, example = "20.5")
    private double price;

    @Min(value = 1, message = "Number of tickets must be at least 1!")
    @ApiModelProperty(notes = "The number of tickets left for the session", required = true, example = "2587")
    private int ticketsLeft;

    @OneToMany(mappedBy = "session")
    @JsonIgnore
    @ApiModelProperty(notes = "The list of the races that take place in the session")
    private List<Race> raceList;

    public Session() {
    }

    public Session(LocalDateTime dateTime, double price, int ticketsLeft) {
        this.dateTime = dateTime;
        this.price = price;
        this.ticketsLeft = ticketsLeft;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getTicketsLeft() {
        return ticketsLeft;
    }

    public void setTicketsLeft(int ticketsLeft) {
        this.ticketsLeft = ticketsLeft;
    }

    public List<Race> getRaceList() {
        return raceList;
    }

    public void setRaceList(List<Race> raceList) {
        this.raceList = raceList;
    }
}
