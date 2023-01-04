package com.example.swimmingchampionship.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Pattern;

@ApiModel(value = "Times request", description = "The details needed to set the times for a race")
public class TimesDto {
    @Pattern(regexp = "^([0-5][0-9]:)?[0-5][0-9].[0-9][0-9]$", message = "Lane 1 time must be in MI:SS.CS or SS.CS format!")
    @ApiModelProperty(notes = "The time (in MI:SS.CS or SS.CS format) recorded for the swimmer on lane 1", example = "47.36")
    private String timeLane1;

    @Pattern(regexp = "^([0-5][0-9]:)?[0-5][0-9].[0-9][0-9]$", message = "Lane 2 time must be in MI:SS.CS or SS.CS format!")
    @ApiModelProperty(notes = "The time (in MI:SS.CS or SS.CS format) recorded for the swimmer on lane 2", example = "46.98")
    private String timeLane2;

    @Pattern(regexp = "^([0-5][0-9]:)?[0-5][0-9].[0-9][0-9]$", message = "Lane 3 time must be in MI:SS.CS or SS.CS format!")
    @ApiModelProperty(notes = "The time (in MI:SS.CS or SS.CS format) recorded for the swimmer on lane 3", example = "47.07")
    private String timeLane3;

    @Pattern(regexp = "^([0-5][0-9]:)?[0-5][0-9].[0-9][0-9]$", message = "Lane 4 time must be in MI:SS.CS or SS.CS format!")
    @ApiModelProperty(notes = "The time (in MI:SS.CS or SS.CS format) recorded for the swimmer on lane 4", example = "47.54")
    private String timeLane4;

    public TimesDto(String timeLane1, String timeLane2, String timeLane3, String timeLane4) {
        this.timeLane1 = timeLane1;
        this.timeLane2 = timeLane2;
        this.timeLane3 = timeLane3;
        this.timeLane4 = timeLane4;
    }

    public String getTimeLane1() {
        return timeLane1;
    }

    public void setTimeLane1(String timeLane1) {
        this.timeLane1 = timeLane1;
    }

    public String getTimeLane2() {
        return timeLane2;
    }

    public void setTimeLane2(String timeLane2) {
        this.timeLane2 = timeLane2;
    }

    public String getTimeLane3() {
        return timeLane3;
    }

    public void setTimeLane3(String timeLane3) {
        this.timeLane3 = timeLane3;
    }

    public String getTimeLane4() {
        return timeLane4;
    }

    public void setTimeLane4(String timeLane4) {
        this.timeLane4 = timeLane4;
    }
}
