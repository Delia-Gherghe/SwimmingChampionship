package com.example.swimmingchampionship.model;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Entity
@ApiModel(value = "Race details", description = "Full information stored for a race")
public class Race {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(notes = "The id of the race", required = true, example = "1")
    private int id;

    @NotBlank(message = "Race name can't be empty or null!")
    @ApiModelProperty(required = true, notes = "The name of the race", example = "Heat 1")
    private String name;

    @NotNull(message = "Race round must have a value!")
    @Enumerated(EnumType.STRING)
    @ApiModelProperty(notes = "The round of the race", required = true, example = "Heat")
    private RoundType round;

    @NotNull(message = "Race event must have a value!")
    @ManyToOne
    @JoinColumn(name = "event_id")
    @ApiModelProperty(notes = "The event the race belongs to", required = true)
    private Event event;

    @NotNull(message = "Race session must have a value!")
    @ManyToOne
    @JoinColumn(name = "session_id")
    @ApiModelProperty(notes = "The session the race is scheduled for", required = true)
    private Session session;

    @NotNull(message = "Race start time must have a value!")
    @Pattern(regexp = "^(0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$", message = "Start time must be in HH24:MI format!")
    @ApiModelProperty(required = true, notes = "The time (in HH24:MI format) the race is scheduled to start", example = "09:30")
    private String startTime;

    @ManyToOne
    @JoinColumn(name = "lane1_swimmer_id")
    @ApiModelProperty(notes = "The swimmer on lane 1")
    private Swimmer swimmerLane1;

    @ManyToOne
    @JoinColumn(name = "lane2_swimmer_id")
    @ApiModelProperty(notes = "The swimmer on lane 2")
    private Swimmer swimmerLane2;

    @ManyToOne
    @JoinColumn(name = "lane3_swimmer_id")
    @ApiModelProperty(notes = "The swimmer on lane 3")
    private Swimmer swimmerLane3;

    @ManyToOne
    @JoinColumn(name = "lane4_swimmer_id")
    @ApiModelProperty(notes = "The swimmer on lane 4")
    private Swimmer swimmerLane4;

    @Pattern(regexp = "^([0-5][0-9]:)?[0-5][0-9].[0-9][0-9]$", message = "Lane 1 time must be in MI:SS.CS or SS.CS format!")
    @ApiModelProperty(notes = "The time (in MI:SS.CS or SS.CS format) recorded for the swimmer on lane 1", example = "47.38")
    private String timeLane1;

    @Pattern(regexp = "^([0-5][0-9]:)?[0-5][0-9].[0-9][0-9]$", message = "Lane 2 time must be in MI:SS.CS or SS.CS format!")
    @ApiModelProperty(notes = "The time (in MI:SS.CS or SS.CS format) recorded for the swimmer on lane 2", example = "46.86")
    private String timeLane2;

    @Pattern(regexp = "^([0-5][0-9]:)?[0-5][0-9].[0-9][0-9]$", message = "Lane 3 time must be in MI:SS.CS or SS.CS format!")
    @ApiModelProperty(notes = "The time (in MI:SS.CS or SS.CS format) recorded for the swimmer on lane 3", example = "47.05")
    private String timeLane3;

    @Pattern(regexp = "^([0-5][0-9]:)?[0-5][0-9].[0-9][0-9]$", message = "Lane 4 time must be in MI:SS.CS or SS.CS format!")
    @ApiModelProperty(notes = "The time (in MI:SS.CS or SS.CS format) recorded for the swimmer on lane 4", example = "47.61")
    private String timeLane4;

    public Race() {
    }

    public Race(String name, RoundType round, Event event, Session session, String startTime, Swimmer swimmerLane1, Swimmer swimmerLane2, Swimmer swimmerLane3, Swimmer swimmerLane4, String timeLane1, String timeLane2, String timeLane3, String timeLane4) {
        this.name = name;
        this.round = round;
        this.event = event;
        this.session = session;
        this.startTime = startTime;
        this.swimmerLane1 = swimmerLane1;
        this.swimmerLane2 = swimmerLane2;
        this.swimmerLane3 = swimmerLane3;
        this.swimmerLane4 = swimmerLane4;
        this.timeLane1 = timeLane1;
        this.timeLane2 = timeLane2;
        this.timeLane3 = timeLane3;
        this.timeLane4 = timeLane4;
    }

    public Race(String name, RoundType round, Event event, Session session, String startTime, Swimmer swimmerLane1, Swimmer swimmerLane2, Swimmer swimmerLane3, Swimmer swimmerLane4) {
        this.name = name;
        this.round = round;
        this.event = event;
        this.session = session;
        this.startTime = startTime;
        this.swimmerLane1 = swimmerLane1;
        this.swimmerLane2 = swimmerLane2;
        this.swimmerLane3 = swimmerLane3;
        this.swimmerLane4 = swimmerLane4;
    }

    public Race(String name, RoundType round, String startTime) {
        this.name = name;
        this.round = round;
        this.startTime = startTime;
    }

    public Race(String name, RoundType round, Event event, Session session, String startTime) {
        this.name = name;
        this.round = round;
        this.event = event;
        this.session = session;
        this.startTime = startTime;
    }

    public Race(Swimmer swimmerLane1, Swimmer swimmerLane2, Swimmer swimmerLane3, Swimmer swimmerLane4, String timeLane1, String timeLane2, String timeLane3, String timeLane4) {
        this.swimmerLane1 = swimmerLane1;
        this.swimmerLane2 = swimmerLane2;
        this.swimmerLane3 = swimmerLane3;
        this.swimmerLane4 = swimmerLane4;
        this.timeLane1 = timeLane1;
        this.timeLane2 = timeLane2;
        this.timeLane3 = timeLane3;
        this.timeLane4 = timeLane4;
    }

    public Race(Swimmer swimmerLane1, Swimmer swimmerLane2, Swimmer swimmerLane3, Swimmer swimmerLane4) {
        this.swimmerLane1 = swimmerLane1;
        this.swimmerLane2 = swimmerLane2;
        this.swimmerLane3 = swimmerLane3;
        this.swimmerLane4 = swimmerLane4;
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

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public Swimmer getSwimmerLane1() {
        return swimmerLane1;
    }

    public void setSwimmerLane1(Swimmer swimmerLane1) {
        this.swimmerLane1 = swimmerLane1;
    }

    public Swimmer getSwimmerLane2() {
        return swimmerLane2;
    }

    public void setSwimmerLane2(Swimmer swimmerLane2) {
        this.swimmerLane2 = swimmerLane2;
    }

    public Swimmer getSwimmerLane3() {
        return swimmerLane3;
    }

    public void setSwimmerLane3(Swimmer swimmerLane3) {
        this.swimmerLane3 = swimmerLane3;
    }

    public Swimmer getSwimmerLane4() {
        return swimmerLane4;
    }

    public void setSwimmerLane4(Swimmer swimmerLane4) {
        this.swimmerLane4 = swimmerLane4;
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
