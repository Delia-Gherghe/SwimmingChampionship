package com.example.swimmingchampionship.service;

import com.example.swimmingchampionship.exception.SponsorIdNotFoundException;
import com.example.swimmingchampionship.model.Sponsor;
import com.example.swimmingchampionship.repository.SponsorRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SponsorServiceTest {

    @InjectMocks
    private SponsorService sponsorService;

    @Mock
    private SponsorRepository sponsorRepository;

    @Test
    @DisplayName("Get sponsor by id happy path")
    void getSponsorByIdTest(){
        int id = 1;
        Sponsor sponsor = new Sponsor();
        sponsor.setId(id);
        when(sponsorRepository.findById(id)).thenReturn(Optional.of(sponsor));

        Sponsor result = sponsorService.getSponsorById(id);

        assertNotNull(result);
        assertEquals(sponsor.getId(), result.getId());

        verify(sponsorRepository).findById(id);
    }

    @Test
    @DisplayName("Get sponsor by id not found")
    void getSponsorByIdExceptionTest(){
        int id = 1;
        when(sponsorRepository.findById(id)).thenReturn(Optional.empty());

        SponsorIdNotFoundException exception = assertThrows(SponsorIdNotFoundException.class,
                () -> sponsorService.getSponsorById(id));

        assertNotNull(exception);
        assertEquals("Sponsor with id 1 does not exist!", exception.getMessage());

        verify(sponsorRepository).findById(id);
    }

}