package com.example.swimmingchampionship.controller;

import com.example.swimmingchampionship.model.Sponsor;
import com.example.swimmingchampionship.service.SponsorService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SponsorController.class)
class SponsorControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SponsorService sponsorService;

    @Test
    @DisplayName("Get sponsor by id")
    void getSponsorByIdTest() throws Exception {
        int id = 1;
        Sponsor sponsor = new Sponsor();
        sponsor.setId(id);
        when(sponsorService.getSponsorById(id)).thenReturn(sponsor);
        String url = "/sponsor/{sponsorId}";

        mockMvc.perform(get(url, id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(sponsor.getId()));
    }
}