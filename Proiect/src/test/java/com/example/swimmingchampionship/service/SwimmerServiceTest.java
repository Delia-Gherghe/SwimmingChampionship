package com.example.swimmingchampionship.service;

import com.example.swimmingchampionship.dto.SwimmerRequest;
import com.example.swimmingchampionship.exception.SwimmerIdNotFoundException;
import com.example.swimmingchampionship.mapper.SwimmerMapper;
import com.example.swimmingchampionship.model.Swimmer;
import com.example.swimmingchampionship.repository.SwimmerRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SwimmerServiceTest {
    @InjectMocks
    private SwimmerService swimmerService;

    @Mock
    private SwimmerRepository swimmerRepository;

    @Mock
    private SwimmerMapper swimmerMapper;

    @Test
    @DisplayName("Add swimmer")
    void saveSwimmerTest(){
        SwimmerRequest swimmerRequest = new SwimmerRequest("David", "Popovici", Date.valueOf("2004-09-15"), "Romania");
        Swimmer swimmer = new Swimmer("David", "Popovici", Date.valueOf("2004-09-15"), "Romania");
        Swimmer savedSwimmer = new Swimmer("David", "Popovici", Date.valueOf("2004-09-15"), "Romania");
        when(swimmerMapper.swimmerRequestToSwimmer(swimmerRequest)).thenReturn(swimmer);
        when(swimmerRepository.save(swimmer)).thenReturn(savedSwimmer);

        Swimmer result = swimmerService.saveSwimmer(swimmerRequest);

        assertNotNull(result);
        assertEquals(savedSwimmer.getFirstName(), result.getFirstName());
        assertEquals(savedSwimmer.getLastName(), result.getLastName());
        assertEquals(savedSwimmer.getBirthday(), result.getBirthday());
        assertEquals(savedSwimmer.getCountry(), result.getCountry());

        verify(swimmerRepository).save(swimmer);
    }

    @Test
    @DisplayName("Get all swimmers")
    void getAllSwimmersTest(){
        Swimmer swimmer = new Swimmer("David", "Popovici", Date.valueOf("2004-09-15"), "Romania");
        when(swimmerRepository.findAll()).thenReturn(List.of(swimmer));

        List<Swimmer> result = swimmerService.getSwimmers(null);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(swimmer, result.get(0));

        verify(swimmerRepository).findAll();
        verify(swimmerRepository, times(0)).getSwimmerByCountry(any());
    }

    @Test
    @DisplayName("Get swimmers filtered by country")
    void getSwimmersByCountryTest(){
        String country = "Romania";
        Swimmer swimmer = new Swimmer("David", "Popovici", Date.valueOf("2004-09-15"), "Romania");
        when(swimmerRepository.getSwimmerByCountry(country)).thenReturn(List.of(swimmer));

        List<Swimmer> result = swimmerService.getSwimmers(country);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(swimmer, result.get(0));

        verify(swimmerRepository).getSwimmerByCountry(country);
        verify(swimmerRepository, times(0)).findAll();
    }

    @Test
    @DisplayName("Get swimmer by id happy path")
    void getSwimmerByIdTest(){
        int id = 1;
        Swimmer swimmer = new Swimmer();
        swimmer.setId(id);
        when(swimmerRepository.findById(id)).thenReturn(Optional.of(swimmer));

        Swimmer result = swimmerService.getSwimmerById(id);

        assertNotNull(result);
        assertEquals(swimmer.getId(), result.getId());

        verify(swimmerRepository).findById(id);
    }

    @Test
    @DisplayName("Get swimmer by id not found")
    void getSwimmerByIdExceptionTest(){
        int id = 1;
        when(swimmerRepository.findById(id)).thenReturn(Optional.empty());

        SwimmerIdNotFoundException exception = assertThrows(SwimmerIdNotFoundException.class,
                () -> swimmerService.getSwimmerById(id));

        assertNotNull(exception);
        assertEquals("Swimmer with id 1 does not exist!", exception.getMessage());

        verify(swimmerRepository).findById(id);
    }
}