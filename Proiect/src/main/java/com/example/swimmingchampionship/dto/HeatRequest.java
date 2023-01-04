package com.example.swimmingchampionship.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@ApiModel(value = "Heat request", description = "The details needed to add a new heat")
public class HeatRequest {
    @NotBlank(message = "Race name cannot be blank!")
    @ApiModelProperty(required = true, notes = "The name of the race", example = "Heat 1")
    private String name;

    @NotNull(message = "Race event must have a value!")
    @ApiModelProperty(required = true, notes = "The id of the event the race belongs to", example = "1")
    private Integer eventId;

    @NotNull(message = "Race session must have a value!")
    @ApiModelProperty(required = true, notes = "The id of the session the race is scheduled for", example = "1")
    private Integer sessionId;

    @NotNull(message = "Race start time must have a value!")
    @Pattern(regexp = "^(0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$", message = "Start time must be in HH24:MI format!")
    @ApiModelProperty(required = true, notes = "The time (in HH24:MI format) the Heat is scheduled to start", example = "09:30")
    private String startTime;

    @ApiModelProperty(notes = "The id of the swimmer on lane 1", example = "5")
    private Integer lane1SwimmerId;
    @ApiModelProperty(notes = "The id of the swimmer on lane 2", example = "6")
    private Integer lane2SwimmerId;
    @ApiModelProperty(notes = "The id of the swimmer on lane 3", example = "7")
    private Integer lane3SwimmerId;
    @ApiModelProperty(notes = "The id of the swimmer on lane 4", example = "8")
    private Integer lane4SwimmerId;

    public HeatRequest(String name, Integer eventId, Integer sessionId, String startTime, Integer lane1SwimmerId, Integer lane2SwimmerId, Integer lane3SwimmerId, Integer lane4SwimmerId) {
        this.name = name;
        this.eventId = eventId;
        this.sessionId = sessionId;
        this.startTime = startTime;
        this.lane1SwimmerId = lane1SwimmerId;
        this.lane2SwimmerId = lane2SwimmerId;
        this.lane3SwimmerId = lane3SwimmerId;
        this.lane4SwimmerId = lane4SwimmerId;
    }

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    public void setSessionId(Integer sessionId) {
        this.sessionId = sessionId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSessionId() {
        return sessionId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public Integer getLane1SwimmerId() {
        return lane1SwimmerId;
    }

    public void setLane1SwimmerId(Integer lane1SwimmerId) {
        this.lane1SwimmerId = lane1SwimmerId;
    }

    public Integer getLane2SwimmerId() {
        return lane2SwimmerId;
    }

    public void setLane2SwimmerId(Integer lane2SwimmerId) {
        this.lane2SwimmerId = lane2SwimmerId;
    }

    public Integer getLane3SwimmerId() {
        return lane3SwimmerId;
    }

    public void setLane3SwimmerId(Integer lane3SwimmerId) {
        this.lane3SwimmerId = lane3SwimmerId;
    }

    public Integer getLane4SwimmerId() {
        return lane4SwimmerId;
    }

    public void setLane4SwimmerId(Integer lane4SwimmerId) {
        this.lane4SwimmerId = lane4SwimmerId;
    }
}
