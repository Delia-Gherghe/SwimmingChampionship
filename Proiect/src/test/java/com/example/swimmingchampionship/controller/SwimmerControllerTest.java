package com.example.swimmingchampionship.controller;

import com.example.swimmingchampionship.dto.SwimmerRequest;
import com.example.swimmingchampionship.model.Swimmer;
import com.example.swimmingchampionship.service.SwimmerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SwimmerController.class)
class SwimmerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private SwimmerService swimmerService;

    @Test
    @DisplayName("Add new swimmer happy path")
    void addNewSwimmerTest() throws Exception {
        SwimmerRequest swimmerRequest = new SwimmerRequest("David", "Popovici", null, "Romania");
        Swimmer swimmer = new Swimmer("David", "Popovici", null, "Romania");
        when(swimmerService.saveSwimmer(any())).thenReturn(swimmer);

        mockMvc.perform(post("/swimmer").contentType("application/json")
                .content(objectMapper.writeValueAsString(swimmerRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName").value(swimmerRequest.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(swimmerRequest.getLastName()))
                .andExpect(jsonPath("$.country").value(swimmerRequest.getCountry()));
    }

    @Test
    @DisplayName("Add new swimmer validation error")
    void addNewSwimmerExceptionTest() throws Exception {
        SwimmerRequest swimmerRequest = new SwimmerRequest("David", "Popovici", null, null);

        MvcResult requestResult = mockMvc.perform(post("/swimmer").contentType("application/json")
                .content(objectMapper.writeValueAsString(swimmerRequest)))
                .andExpect(status().isBadRequest()).andReturn();

        String result = requestResult.getResponse().getContentAsString();
        assertEquals("Country must not be empty!", result);
    }

    @Test
    @DisplayName("Get all swimmers")
    void getAllSwimmersTest() throws Exception {
        Swimmer swimmer = new Swimmer("David", "Popovici", null, "Romania");
        List<Swimmer> swimmers = List.of(swimmer);
        when(swimmerService.getSwimmers(null)).thenReturn(swimmers);

        mockMvc.perform(get("/swimmer"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName").value(swimmer.getFirstName()))
                .andExpect(jsonPath("$[0].lastName").value(swimmer.getLastName()))
                .andExpect(jsonPath("$[0].country").value(swimmer.getCountry()));
    }

    @Test
    @DisplayName("Get swimmers by country")
    void getSwimmersByCountryTest() throws Exception {
        String country = "Romania";
        Swimmer swimmer = new Swimmer("David", "Popovici", null, "Romania");
        List<Swimmer> swimmers = List.of(swimmer);
        when(swimmerService.getSwimmers(country)).thenReturn(swimmers);

        mockMvc.perform(get("/swimmer").param("country", country))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName").value(swimmer.getFirstName()))
                .andExpect(jsonPath("$[0].lastName").value(swimmer.getLastName()))
                .andExpect(jsonPath("$[0].country").value(country));
    }
}