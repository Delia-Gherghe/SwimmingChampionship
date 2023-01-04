package com.example.swimmingchampionship.dto;

import com.example.swimmingchampionship.model.RoundType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@ApiModel(value = "Race Dto", description = "The details of a race without showing the session and complete swimmer information")
public class RaceDto {
    @ApiModelProperty(notes = "The id of the race", required = true, example = "1")
    private int id;
    @NotEmpty(message = "Race name must not be empty!")
    @ApiModelProperty(notes = "The name of the race", required = true, example = "Heat 1")
    private String name;
    @NotNull(message = "Race round must have a value!")
    @ApiModelProperty(notes = "The round of the race", required = true, example = "Heat")
    private RoundType round;
    @NotEmpty(message = "Race event name must not be empty!")
    @ApiModelProperty(notes = "The name of the event the race belongs to", required = true, example = "100 Freestyle")
    private String eventName;
    @NotNull(message = "Race start time must have a value!")
    @Pattern(regexp = "^(0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$", message = "Start time must be in HH24:MI format!")
    @ApiModelProperty(required = true, notes = "The time (in HH24:MI format) the race is scheduled to start", example = "09:30")
    private String startTime;
    @ApiModelProperty(notes = "The full name of the swimmer on lane 1", example = "Maxime Grousset")
    private String swimmerLane1;
    @Pattern(regexp = "^([0-5][0-9]:)?[0-5][0-9].[0-9][0-9]$", message = "Lane 1 time must be in MI:SS.CS or SS.CS format!")
    @ApiModelProperty(notes = "The time (in MI:SS.CS or SS.CS format) recorded for the swimmer on lane 1", example = "47.38")
    private String timeLane1;
    @ApiModelProperty(notes = "The full name of the swimmer on lane 2", example = "David Popovici")
    private String swimmerLane2;
    @Pattern(regexp = "^([0-5][0-9]:)?[0-5][0-9].[0-9][0-9]$", message = "Lane 2 time must be in MI:SS.CS or SS.CS format!")
    @ApiModelProperty(notes = "The time (in MI:SS.CS or SS.CS format) recorded for the swimmer on lane 2", example = "46.86")
    private String timeLane2;
    @ApiModelProperty(notes = "The full name of the swimmer on lane 3", example = "Caleb Dressel")
    private String swimmerLane3;
    @Pattern(regexp = "^([0-5][0-9]:)?[0-5][0-9].[0-9][0-9]$", message = "Lane 3 time must be in MI:SS.CS or SS.CS format!")
    @ApiModelProperty(notes = "The time (in MI:SS.CS or SS.CS format) recorded for the swimmer on lane 3", example = "47.16")
    private String timeLane3;
    @ApiModelProperty(notes = "The full name of the swimmer on lane 4", example = "Joshua Liendo Edwards")
    private String swimmerLane4;
    @Pattern(regexp = "^([0-5][0-9]:)?[0-5][0-9].[0-9][0-9]$", message = "Lane 4 time must be in MI:SS.CS or SS.CS format!")
    @ApiModelProperty(notes = "The time (in MI:SS.CS or SS.CS format) recorded for the swimmer on lane 4", example = "47.45")
    private String timeLane4;

    public RaceDto(int id, String name, RoundType round, String eventName, String startTime, String swimmerLane1, String timeLane1, String swimmerLane2, String timeLane2, String swimmerLane3, String timeLane3, String swimmerLane4, String timeLane4) {
        this.id = id;
        this.name = name;
        this.round = round;
        this.eventName = eventName;
        this.startTime = startTime;
        this.swimmerLane1 = swimmerLane1;
        this.timeLane1 = timeLane1;
        this.swimmerLane2 = swimmerLane2;
        this.timeLane2 = timeLane2;
        this.swimmerLane3 = swimmerLane3;
        this.timeLane3 = timeLane3;
        this.swimmerLane4 = swimmerLane4;
        this.timeLane4 = timeLane4;
    }

    public RaceDto(String name, RoundType round, String startTime) {
        this.name = name;
        this.round = round;
        this.startTime = startTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RoundType getRound() {
        return round;
    }

    public void setRound(RoundType round) {
        this.round = round;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getSwimmerLane1() {
        return swimmerLane1;
    }

    public void setSwimmerLane1(String swimmerLane1) {
        this.swimmerLane1 = swimmerLane1;
    }

    public String getTimeLane1() {
        return timeLane1;
    }

    public void setTimeLane1(String timeLane1) {
        this.timeLane1 = timeLane1;
    }

    public String getSwimmerLane2() {
        return swimmerLane2;
    }

    public void setSwimmerLane2(String swimmerLane2) {
        this.swimmerLane2 = swimmerLane2;
    }

    public String getTimeLane2() {
        return timeLane2;
    }

    public void setTimeLane2(String timeLane2) {
        this.timeLane2 = timeLane2;
    }

    public String getSwimmerLane3() {
        return swimmerLane3;
    }

    public void setSwimmerLane3(String swimmerLane3) {
        this.swimmerLane3 = swimmerLane3;
    }

    public String getTimeLane3() {
        return timeLane3;
    }

    public void setTimeLane3(String timeLane3) {
        this.timeLane3 = timeLane3;
    }

    public String getSwimmerLane4() {
        return swimmerLane4;
    }

    public void setSwimmerLane4(String swimmerLane4) {
        this.swimmerLane4 = swimmerLane4;
    }

    public String getTimeLane4() {
        return timeLane4;
    }

    public void setTimeLane4(String timeLane4) {
        this.timeLane4 = timeLane4;
    }
}
