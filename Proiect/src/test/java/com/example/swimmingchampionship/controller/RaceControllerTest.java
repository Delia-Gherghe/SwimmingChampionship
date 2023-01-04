package com.example.swimmingchampionship.controller;

import com.example.swimmingchampionship.dto.FinalStageRequest;
import com.example.swimmingchampionship.dto.HeatRequest;
import com.example.swimmingchampionship.dto.TimesDto;
import com.example.swimmingchampionship.model.*;
import com.example.swimmingchampionship.service.RaceService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RaceController.class)
class RaceControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private RaceService raceService;

    @Test
    @DisplayName("Get race by id")
    void getRaceByIdTest() throws Exception {
        int id = 1;
        Race race = new Race();
        race.setId(id);
        String url = "/race/{raceId}";
        when(raceService.getRaceById(id)).thenReturn(race);

        mockMvc.perform(get(url, id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(race.getId()));
    }

    @Test
    @DisplayName("Set times happy path")
    void updateTimesTest() throws Exception {
        TimesDto times = new TimesDto("01:46.22", "01:45.15", "01:45.87", "01:46.06");
        int raceId = 1;
        Race race = new Race();
        race.setId(raceId);
        race.setTimeLane1("01:46.22");
        race.setTimeLane2("01:45.15");
        race.setTimeLane3("01:45.87");
        race.setTimeLane4("01:46.06");
        String url = "/race/{raceId}";
        when(raceService.updateTimes(eq(raceId), any(TimesDto.class))).thenReturn(race);

        mockMvc.perform(put(url, raceId).contentType("application/json").content(objectMapper.writeValueAsString(times)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(race.getId()))
                .andExpect(jsonPath("$.timeLane1").value(times.getTimeLane1()))
                .andExpect(jsonPath("$.timeLane2").value(times.getTimeLane2()))
                .andExpect(jsonPath("$.timeLane3").value(times.getTimeLane3()))
                .andExpect(jsonPath("$.timeLane4").value(times.getTimeLane4()));
    }

    @Test
    @DisplayName("Set times invalid time")
    void updateTimesExceptionTest() throws Exception {
        TimesDto times = new TimesDto("01:46.22", "01:45.15", "1:45", "01:46.06");
        int raceId = 1;
        String url = "/race/{raceId}";

        MvcResult requestResult = mockMvc.perform(put(url, raceId).contentType("application/json")
                .content(objectMapper.writeValueAsString(times)))
                .andExpect(status().isBadRequest()).andReturn();

        String result = requestResult.getResponse().getContentAsString();
        assertEquals("Lane 3 time must be in MI:SS.CS or SS.CS format!", result);
    }

    @Test
    @DisplayName("Add heat happy path")
    void addHeatTest() throws Exception {
        int eventId = 1;
        Event event = new Event();
        event.setId(eventId);
        int sessionId = 1;
        Session session = new Session();
        session.setId(sessionId);
        int swimmer1Id = 1;
        int swimmer2Id = 2;
        int swimmer3Id = 3;
        int swimmer4Id = 4;
        Swimmer swimmer1 = new Swimmer();
        swimmer1.setId(swimmer1Id);
        Swimmer swimmer2 = new Swimmer();
        swimmer2.setId(swimmer2Id);
        Swimmer swimmer3 = new Swimmer();
        swimmer3.setId(swimmer3Id);
        Swimmer swimmer4 = new Swimmer();
        swimmer4.setId(swimmer4Id);
        HeatRequest heatRequest =  new HeatRequest("Heat 1", eventId, sessionId, "09:25", swimmer1Id, swimmer2Id, swimmer3Id, swimmer4Id);
        Race race = new Race("Heat 1", RoundType.Heat, event, session, "09:25", swimmer1, swimmer2, swimmer3, swimmer4);
        when(raceService.addHeat(any())).thenReturn(race);
        String url = "/race/heat";

        mockMvc.perform(post(url).contentType("application/json").content(objectMapper.writeValueAsString(heatRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(heatRequest.getName()))
                .andExpect(jsonPath("$.event.id").value(heatRequest.getEventId()))
                .andExpect(jsonPath("$.session.id").value(heatRequest.getSessionId()))
                .andExpect(jsonPath("$.startTime").value(heatRequest.getStartTime()))
                .andExpect(jsonPath("$.swimmerLane1.id").value(heatRequest.getLane1SwimmerId()))
                .andExpect(jsonPath("$.swimmerLane2.id").value(heatRequest.getLane2SwimmerId()))
                .andExpect(jsonPath("$.swimmerLane3.id").value(heatRequest.getLane3SwimmerId()))
                .andExpect(jsonPath("$.swimmerLane4.id").value(heatRequest.getLane4SwimmerId()));
    }

    @Test
    @DisplayName("Add heat invalid body")
    void addHeatExceptionTest() throws Exception {
        HeatRequest heatRequest =  new HeatRequest("Heat 1", null, 1, "9", 1, 2, 3, 4);
        String url = "/race/heat";

        MvcResult requestResult = mockMvc.perform(post(url).contentType("application/json")
                .content(objectMapper.writeValueAsString(heatRequest)))
                .andExpect(status().isBadRequest()).andReturn();
        String result = requestResult.getResponse().getContentAsString();
        assertThat(result, anyOf(is("Start time must be in HH24:MI format!\nRace event must have a value!"),
                is("Race event must have a value!\nStart time must be in HH24:MI format!")));
    }

    @Test
    @DisplayName("Generate final stage happy path")
    void generateFinalStageTest() throws Exception {
        int eventId = 1;
        Event event = new Event();
        event.setId(eventId);
        RoundType round = RoundType.Semifinal;
        Race race = new Race();
        race.setEvent(event);
        List<Race> races = List.of(race);
        when(raceService.generateFinalStage(eventId, round)).thenReturn(races);
        String url = "/race/generate/{eventId}";

        mockMvc.perform(post(url, eventId).param("round", String.valueOf(round)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$[0].event.id").value(event.getId()));
    }

    @Test
    @DisplayName("Generate final stage invalid round")
    void generateFinalStageExceptionTest() throws Exception {
        int eventId = 1;
        RoundType round = RoundType.Heat;
        String url = "/race/generate/{eventId}";

        MvcResult requestResult = mockMvc.perform(post(url, eventId).param("round", String.valueOf(round)))
                .andExpect(status().isBadRequest()).andReturn();
        String result = requestResult.getResponse().getContentAsString();
        assertEquals("Round must be semifinal or final!", result);
    }

    @Test
    @DisplayName("Add final stage happy path")
    void addFinalStageTest() throws Exception {
        int eventId = 1;
        Event event = new Event();
        event.setId(eventId);
        int sessionId = 1;
        Session session = new Session();
        session.setId(sessionId);
        FinalStageRequest finalStageRequest = new FinalStageRequest("Semifinal 1", RoundType.Semifinal, eventId, sessionId, "18:37");
        Race race = new Race("Semifinal 1", RoundType.Semifinal, event, session, "18:37");
        when(raceService.scheduleFinalStage(any())).thenReturn(race);
        String url = "/race/finalStage";

        mockMvc.perform(post(url).contentType("application/json").content(objectMapper.writeValueAsString(finalStageRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(finalStageRequest.getName()))
                .andExpect(jsonPath("$.event.id").value(finalStageRequest.getEventId()))
                .andExpect(jsonPath("$.session.id").value(finalStageRequest.getSessionId()))
                .andExpect(jsonPath("$.startTime").value(finalStageRequest.getStartTime()));
    }

    @Test
    @DisplayName("Add final stage invalid body")
    void addFinalStageExceptionTest() throws Exception {
        FinalStageRequest finalStageRequest = new FinalStageRequest(null, RoundType.Semifinal, 1, 1, "18:37");
        String url = "/race/finalStage";

        MvcResult requestResult = mockMvc.perform(post(url).contentType("application/json")
                .content(objectMapper.writeValueAsString(finalStageRequest)))
                .andExpect(status().isBadRequest()).andReturn();
        String result = requestResult.getResponse().getContentAsString();
        assertEquals("Race name must not be empty!", result);
    }

    @Test
    @DisplayName("Replace withdrawn swimmer")
    void replaceWithdrawnSwimmerTest() throws Exception {
        int raceId = 1;
        int swimmerId = 1;
        Race race = new Race();
        race.setId(raceId);
        when(raceService.replaceSwimmer(raceId, swimmerId)).thenReturn(race);
        String url = "/race/{raceId}/withdraw";

        mockMvc.perform(put(url, raceId).param("swimmerId", String.valueOf(swimmerId)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(race.getId()));
    }
}