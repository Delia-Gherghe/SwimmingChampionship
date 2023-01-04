package com.example.swimmingchampionship.dto;

import com.example.swimmingchampionship.model.RoundType;
import com.example.swimmingchampionship.validation.FinalStage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@ApiModel(value = "Final stage request", description = "Required details needed to schedule a semifinal/final")
public class FinalStageRequest {
    @NotEmpty(message = "Race name must not be empty!")
    @ApiModelProperty(required = true, notes = "The name of the race", example = "Semifinal 1")
    private String name;

    @NotNull(message = "Race round must have a value!")
    @FinalStage
    @ApiModelProperty(required = true, notes = "The round of the final stage", example = "Semifinal")
    private RoundType round;

    @NotNull(message = "Race event must have a value!")
    @ApiModelProperty(required = true, notes = "The id of the event the race belongs to", example = "1")
    private Integer eventId;

    @NotNull(message = "Race session must have a value!")
    @ApiModelProperty(required = true, notes = "The id of the session the race is scheduled for", example = "1")
    private Integer sessionId;

    @NotNull(message = "Race start time must have a value!")
    @Pattern(regexp = "^(0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$", message = "Start time must be in HH24:MI format!")
    @ApiModelProperty(required = true, notes = "The time (in HH24:MI format) the race is scheduled to start", example = "18:30")
    private String startTime;

    public FinalStageRequest(String name, RoundType round, Integer eventId, Integer sessionId, String startTime) {
        this.name = name;
        this.round = round;
        this.eventId = eventId;
        this.sessionId = sessionId;
        this.startTime = startTime;
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

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    public Integer getSessionId() {
        return sessionId;
    }

    public void setSessionId(Integer sessionId) {
        this.sessionId = sessionId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }
}
