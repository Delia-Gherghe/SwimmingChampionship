package com.example.swimmingchampionship.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@ApiModel(value = "Event details", description = "Full information stored for an event")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(notes = "The id of the event", required = true, example = "1")
    private int id;

    @NotNull(message = "Event style must have a value!")
    @Enumerated(EnumType.STRING)
    @ApiModelProperty(notes = "The stroke type of the event", required = true, example = "Backstroke")
    private StrokeType style;

    @Min(value = 50, message = "Event distance must be grater then 50m!")
    @Max(value = 1500, message = "Event distance must not be greater than 1500m!")
    @ApiModelProperty(notes = "The distance of the swim in the event", required = true, example = "200")
    private int distance;

    @NotNull(message = "Choose if event has a semifinal round")
    @ApiModelProperty(notes = "Whether the event has a semifinal round", required = true, example = "true")
    private boolean hasSemifinal;

    public Event() {
    }

    public Event(StrokeType style, int distance, boolean hasSemifinal) {
        this.style = style;
        this.distance = distance;
        this.hasSemifinal = hasSemifinal;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public StrokeType getStyle() {
        return style;
    }

    public void setStyle(StrokeType style) {
        this.style = style;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public boolean getHasSemifinal() {
        return hasSemifinal;
    }

    public void setHasSemifinal(boolean hasSemifinal) {
        this.hasSemifinal = hasSemifinal;
    }
}
