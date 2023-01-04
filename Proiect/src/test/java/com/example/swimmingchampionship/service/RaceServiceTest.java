package com.example.swimmingchampionship.service;

import com.example.swimmingchampionship.dto.FinalStageRequest;
import com.example.swimmingchampionship.dto.HeatRequest;
import com.example.swimmingchampionship.dto.TimesDto;
import com.example.swimmingchampionship.exception.*;
import com.example.swimmingchampionship.mapper.RaceRequestMapper;
import com.example.swimmingchampionship.model.*;
import com.example.swimmingchampionship.repository.RaceRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RaceServiceTest {
    @InjectMocks
    private RaceService raceService;

    @Mock
    private RaceRepository raceRepository;

    @Mock
    private EventService eventService;

    @Mock
    private SessionService sessionService;

    @Mock
    private SwimmerService swimmerService;

    @Mock
    private RaceRequestMapper raceRequestMapper;

    @Test
    @DisplayName("Get race by id happy path")
    void getRaceByIdTest(){
        int id = 1;
        Race race = new Race();
        race.setId(id);
        when(raceRepository.findById(id)).thenReturn(Optional.of(race));

        Race result = raceService.getRaceById(id);

        assertNotNull(result);
        assertEquals(race.getId(), result.getId());

        verify(raceRepository).findById(id);
    }

    @Test
    @DisplayName("Get race by id not found")
    void getRaceByIdExceptionTest(){
        int id = 1;
        when(raceRepository.findById(id)).thenReturn(Optional.empty());

        RaceIdNotFoundException exception = assertThrows(RaceIdNotFoundException.class,
                () -> raceService.getRaceById(id));

        assertNotNull(exception);
        assertEquals("Race with id 1 does not exist!", exception.getMessage());

        verify(raceRepository).findById(id);
    }

    @Test
    @DisplayName("Set times")
    void updateTimesTest(){
        int id = 1;
        Race race = new Race();
        race.setId(id);
        TimesDto timesDto = new TimesDto("48.30", "47.08", "47.76", "48.18");
        when(raceRepository.findById(id)).thenReturn(Optional.of(race));
        when(raceRepository.save(race)).thenReturn(race);

        Race result = raceService.updateTimes(id, timesDto);

        assertNotNull(result);
        assertEquals(race.getId(), result.getId());
        assertEquals(timesDto.getTimeLane1(), result.getTimeLane1());
        assertEquals(timesDto.getTimeLane2(), result.getTimeLane2());
        assertEquals(timesDto.getTimeLane3(), result.getTimeLane3());
        assertEquals(timesDto.getTimeLane4(), result.getTimeLane4());

        verify(raceRepository).findById(id);
        verify(raceRepository).save(race);
    }

    @Test
    @DisplayName("Add heat with all swimmers")
    void addHeatAllSwimmersTest(){
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
        when(eventService.getEventById(eventId)).thenReturn(event);
        when(sessionService.getSessionById(sessionId)).thenReturn(session);
        when(swimmerService.getSwimmerById(swimmer1Id)).thenReturn(swimmer1);
        when(swimmerService.getSwimmerById(swimmer2Id)).thenReturn(swimmer2);
        when(swimmerService.getSwimmerById(swimmer3Id)).thenReturn(swimmer3);
        when(swimmerService.getSwimmerById(swimmer4Id)).thenReturn(swimmer4);
        Race race = new Race("Heat 1", RoundType.Heat, "09:25");
        when(raceRequestMapper.heatRequestToRace(heatRequest)).thenReturn(race);
        when(raceRepository.save(race)).thenReturn(race);

        Race result = raceService.addHeat(heatRequest);

        assertNotNull(result);
        assertEquals(heatRequest.getName(), result.getName());
        assertEquals(RoundType.Heat, result.getRound());
        assertEquals(heatRequest.getStartTime(), result.getStartTime());
        assertEquals(heatRequest.getEventId(), result.getEvent().getId());
        assertEquals(heatRequest.getSessionId(), result.getSession().getId());
        assertEquals(heatRequest.getLane1SwimmerId(), result.getSwimmerLane1().getId());
        assertEquals(heatRequest.getLane2SwimmerId(), result.getSwimmerLane2().getId());
        assertEquals(heatRequest.getLane3SwimmerId(), result.getSwimmerLane3().getId());
        assertEquals(heatRequest.getLane4SwimmerId(), result.getSwimmerLane4().getId());

        verify(eventService).getEventById(eventId);
        verify(sessionService).getSessionById(sessionId);
        verify(swimmerService, times(4)).getSwimmerById(anyInt());
        verify(raceRepository).save(race);
    }

    @Test
    @DisplayName("Add heat with missing swimmers")
    void addHeatMissingSwimmersTest(){
        int eventId = 1;
        Event event = new Event();
        event.setId(eventId);
        int sessionId = 1;
        Session session = new Session();
        session.setId(sessionId);
        int swimmer2Id = 2;
        int swimmer3Id = 3;
        Swimmer swimmer2 = new Swimmer();
        swimmer2.setId(swimmer2Id);
        Swimmer swimmer3 = new Swimmer();
        swimmer3.setId(swimmer3Id);
        HeatRequest heatRequest =  new HeatRequest("Heat 1", eventId, sessionId, "09:25", null, swimmer2Id, swimmer3Id, null);
        when(eventService.getEventById(eventId)).thenReturn(event);
        when(sessionService.getSessionById(sessionId)).thenReturn(session);
        when(swimmerService.getSwimmerById(swimmer2Id)).thenReturn(swimmer2);
        when(swimmerService.getSwimmerById(swimmer3Id)).thenReturn(swimmer3);
        Race race = new Race("Heat 1", RoundType.Heat, "09:25");
        when(raceRequestMapper.heatRequestToRace(heatRequest)).thenReturn(race);
        when(raceRepository.save(race)).thenReturn(race);

        Race result = raceService.addHeat(heatRequest);

        assertNotNull(result);
        assertEquals(heatRequest.getName(), result.getName());
        assertEquals(RoundType.Heat, result.getRound());
        assertEquals(heatRequest.getStartTime(), result.getStartTime());
        assertEquals(heatRequest.getEventId(), result.getEvent().getId());
        assertEquals(heatRequest.getSessionId(), result.getSession().getId());
        assertNull(result.getSwimmerLane1());
        assertEquals(heatRequest.getLane2SwimmerId(), result.getSwimmerLane2().getId());
        assertEquals(heatRequest.getLane3SwimmerId(), result.getSwimmerLane3().getId());
        assertNull(result.getSwimmerLane4());

        verify(eventService).getEventById(eventId);
        verify(sessionService).getSessionById(sessionId);
        verify(swimmerService, times(2)).getSwimmerById(anyInt());
        verify(raceRepository).save(race);
    }

    @Test
    @DisplayName("Place swimmers in semifinals of an event with no semifinal round")
    void generateFinalStageSemifinalEventHasNoSemifinalTest(){
        int eventId = 1;
        RoundType roundType = RoundType.Semifinal;
        Event event = new Event(StrokeType.Freestyle, 100, false);
        when(eventService.getEventById(eventId)).thenReturn(event);

        NoSemifinalException exception = assertThrows(NoSemifinalException.class,
                () -> raceService.generateFinalStage(eventId, roundType));

        assertNotNull(exception);
        assertEquals("Event with id 1 does not have a semifinal round", exception.getMessage());

        verify(eventService).getEventById(eventId);
        verify(raceRepository, times(0)).getRaceByEventAndRoundOrderByStartTime(event, roundType);
    }

    @Test
    @DisplayName("Place swimmers in semifinals of an event without 2 semifinals scheduled")
    void generateFinalStageSemifinalEventWithoutSemifinalsScheduledTest(){
        int eventId = 1;
        RoundType roundType = RoundType.Semifinal;
        Event event = new Event(StrokeType.Freestyle, 100, true);
        when(eventService.getEventById(eventId)).thenReturn(event);
        when(raceRepository.getRaceByEventAndRoundOrderByStartTime(event, roundType)).thenReturn(Collections.emptyList());

        WrongNumberOfRacesException exception = assertThrows(WrongNumberOfRacesException.class,
                () -> raceService.generateFinalStage(eventId, roundType));

        assertNotNull(exception);
        assertEquals("Not 2 semifinals scheduled for event with id 1", exception.getMessage());

        verify(eventService).getEventById(eventId);
        verify(raceRepository).getRaceByEventAndRoundOrderByStartTime(event, roundType);
        verify(raceRepository, times(0)).getRaceByEventAndRound(event, RoundType.Heat);
    }

    @Test
    @DisplayName("Place swimmers in semifinals of an event without enough heats")
    void generateFinalStageSemifinalEventWithoutEnoughHeatsTest(){
        int eventId = 1;
        RoundType roundType = RoundType.Semifinal;
        Event event = new Event(StrokeType.Freestyle, 100, true);
        when(eventService.getEventById(eventId)).thenReturn(event);
        when(raceRepository.getRaceByEventAndRoundOrderByStartTime(event, roundType)).thenReturn(List.of(new Race(), new Race()));
        when(raceRepository.getRaceByEventAndRound(event, RoundType.Heat)).thenReturn(Collections.emptyList());

        WrongNumberOfRacesException exception = assertThrows(WrongNumberOfRacesException.class,
                () -> raceService.generateFinalStage(eventId, roundType));

        assertNotNull(exception);
        assertEquals("Not enough heats for event with id 1", exception.getMessage());

        verify(eventService).getEventById(eventId);
        verify(raceRepository).getRaceByEventAndRoundOrderByStartTime(event, roundType);
        verify(raceRepository).getRaceByEventAndRound(event, RoundType.Heat);
        verify(raceRepository, times(0)).saveAll(anyCollection());
    }

    @Test
    @DisplayName("Place swimmers in semifinals of an event without enough swimmers")
    void generateFinalStageSemifinalEventWithoutEnoughSwimmersTest(){
        int eventId = 1;
        RoundType roundType = RoundType.Semifinal;
        Event event = new Event(StrokeType.Freestyle, 100, true);
        when(eventService.getEventById(eventId)).thenReturn(event);
        when(raceRepository.getRaceByEventAndRoundOrderByStartTime(event, roundType)).thenReturn(List.of(new Race(), new Race()));
        when(raceRepository.getRaceByEventAndRound(event, RoundType.Heat)).thenReturn(List.of(new Race(), new Race(), new Race()));

        WrongNumberOfSwimmersException exception = assertThrows(WrongNumberOfSwimmersException.class,
                () -> raceService.generateFinalStage(eventId, roundType));

        assertNotNull(exception);
        assertEquals("Not enough swimmers finished the heats for event with id 1", exception.getMessage());

        verify(eventService).getEventById(eventId);
        verify(raceRepository).getRaceByEventAndRoundOrderByStartTime(event, roundType);
        verify(raceRepository).getRaceByEventAndRound(event, RoundType.Heat);
        verify(raceRepository, times(0)).saveAll(anyCollection());
    }

    @Test
    @DisplayName("Place swimmers in semifinals happy path")
    void generateFinalStageSemifinalTest(){
        int eventId = 1;
        RoundType roundType = RoundType.Semifinal;
        Event event = new Event(StrokeType.Freestyle, 100, true);
        when(eventService.getEventById(eventId)).thenReturn(event);
        Race semifinal1 = new Race();
        Race semifinal2 = new Race();
        List<Race> semifinals = List.of(semifinal1, semifinal2);
        Swimmer swimmer1 = new Swimmer(1, "Mikel", "Schreuders", "Aruba");
        Swimmer swimmer2 = new Swimmer(2, "Dylan", "Carter", "Trinidad and Tobago");
        Swimmer swimmer3 = new Swimmer(3, "Brooks", "Curry", "USA");
        Swimmer swimmer4 = new Swimmer(4, "Nandor", "Nemeth", "Hungary");
        Swimmer swimmer5 = new Swimmer(5, "Lorenzo", "Zazzeri", "Italy");
        Swimmer swimmer6 = new Swimmer(6, "Jacob Henry", "Whittle", "UK");
        Swimmer swimmer7 = new Swimmer(7, "Andrej", "Barna", "Serbia");
        Swimmer swimmer8 = new Swimmer(8, "Caleb", "Dressel", "USA");
        Swimmer swimmer9 = new Swimmer(9, "Zhanle", "Pan", "China");
        Swimmer swimmer10 = new Swimmer(10, "David", "Popovici", "Romania");
        Swimmer swimmer11 = new Swimmer(11, "Maxime", "Grousset", "France");
        Swimmer swimmer12 = new Swimmer(12, "Joshua", "Liendo Edwards", "Canada");
        Race heat1 = new Race(swimmer1, swimmer2, swimmer3, swimmer4, "49.11", "48.88", "49.55", "48.90");
        Race heat2 = new Race(swimmer5, swimmer6, swimmer7, swimmer8, "48.71", "48.23", "49.02", "48.11");
        Race heat3 = new Race(swimmer9, swimmer10, swimmer11, swimmer12, "47.99", "47.39", "47.74", "48.01");
        List<Race> heats = List.of(heat1, heat2, heat3);
        when(raceRepository.getRaceByEventAndRoundOrderByStartTime(event, roundType)).thenReturn(semifinals);
        when(raceRepository.getRaceByEventAndRound(event, RoundType.Heat)).thenReturn(heats);
        when(raceRepository.saveAll(semifinals)).thenReturn(semifinals);

        List<Race> result = raceService.generateFinalStage(eventId, roundType);

        assertNotNull(result);
        assertEquals(heat1.getSwimmerLane2(), result.get(0).getSwimmerLane4());
        assertEquals(heat2.getSwimmerLane1(), result.get(1).getSwimmerLane4());
        assertEquals(heat2.getSwimmerLane2(), result.get(0).getSwimmerLane1());
        assertEquals(heat2.getSwimmerLane4(), result.get(1).getSwimmerLane1());
        assertEquals(heat3.getSwimmerLane4(), result.get(0).getSwimmerLane3());
        assertEquals(heat3.getSwimmerLane1(), result.get(1).getSwimmerLane3());
        assertEquals(heat3.getSwimmerLane3(), result.get(0).getSwimmerLane2());
        assertEquals(heat3.getSwimmerLane2(), result.get(1).getSwimmerLane2());

        verify(eventService).getEventById(eventId);
        verify(raceRepository).getRaceByEventAndRoundOrderByStartTime(event, roundType);
        verify(raceRepository).getRaceByEventAndRound(event, RoundType.Heat);
        verify(raceRepository).saveAll(semifinals);
    }

    @Test
    @DisplayName("Place swimmers in final of an event without 1 final scheduled")
    void generateFinalStageFinalEventWithoutFinalScheduledTest(){
        int eventId = 1;
        RoundType roundType = RoundType.Final;
        Event event = new Event(StrokeType.Freestyle, 100, true);
        when(eventService.getEventById(eventId)).thenReturn(event);
        when(raceRepository.getRaceByEventAndRound(event, roundType)).thenReturn(Collections.emptyList());

        WrongNumberOfRacesException exception = assertThrows(WrongNumberOfRacesException.class,
                () -> raceService.generateFinalStage(eventId, roundType));

        assertNotNull(exception);
        assertEquals("1 final must be scheduled for event with id 1", exception.getMessage());

        verify(eventService).getEventById(eventId);
        verify(raceRepository).getRaceByEventAndRound(event, roundType);
        verify(raceRepository, times(0)).getRaceByEventAndRound(event, RoundType.Semifinal);
    }

    @Test
    @DisplayName("Place swimmers in final of an event without enough races in previous round")
    void generateFinalStageFinalEventWithoutEnoughPreviousRacesTest(){
        int eventId = 1;
        RoundType roundType = RoundType.Final;
        Event event = new Event(StrokeType.Freestyle, 1500, false);
        when(eventService.getEventById(eventId)).thenReturn(event);
        when(raceRepository.getRaceByEventAndRound(event, roundType)).thenReturn(List.of(new Race()));
        when(raceRepository.getRaceByEventAndRound(event, RoundType.Heat)).thenReturn(Collections.emptyList());

        WrongNumberOfRacesException exception = assertThrows(WrongNumberOfRacesException.class,
                () -> raceService.generateFinalStage(eventId, roundType));

        assertNotNull(exception);
        assertEquals("Not enough races from previous round for event with id 1", exception.getMessage());

        verify(eventService).getEventById(eventId);
        verify(raceRepository).getRaceByEventAndRound(event, roundType);
        verify(raceRepository).getRaceByEventAndRound(event, RoundType.Heat);
        verify(raceRepository, times(0)).saveAll(anyCollection());
    }

    @Test
    @DisplayName("Place swimmers in final of an event without enough swimmers")
    void generateFinalStageFinalEventWithoutEnoughSwimmersTest(){
        int eventId = 1;
        RoundType roundType = RoundType.Final;
        Event event = new Event(StrokeType.Freestyle, 100, true);
        when(eventService.getEventById(eventId)).thenReturn(event);
        when(raceRepository.getRaceByEventAndRound(event, roundType)).thenReturn(List.of(new Race()));
        when(raceRepository.getRaceByEventAndRound(event, RoundType.Semifinal)).thenReturn(List.of(new Race(), new Race()));

        WrongNumberOfSwimmersException exception = assertThrows(WrongNumberOfSwimmersException.class,
                () -> raceService.generateFinalStage(eventId, roundType));

        assertNotNull(exception);
        assertEquals("Not enough swimmers finished the previous round for event with id 1", exception.getMessage());

        verify(eventService).getEventById(eventId);
        verify(raceRepository).getRaceByEventAndRound(event, roundType);
        verify(raceRepository).getRaceByEventAndRound(event, RoundType.Semifinal);
        verify(raceRepository, times(0)).saveAll(anyCollection());
    }

    @Test
    @DisplayName("Place swimmers in final happy path")
    void generateFinalStageFinalTest(){
        int eventId = 1;
        RoundType roundType = RoundType.Final;
        Event event = new Event(StrokeType.Freestyle, 100, true);
        when(eventService.getEventById(eventId)).thenReturn(event);
        Race finalRace = new Race();
        List<Race> finals = List.of(finalRace);
        Swimmer swimmer1 = new Swimmer(1, "Lorenzo", "Zazzeri", "Italy");
        Swimmer swimmer2 = new Swimmer(2, "Jacob Henry", "Whittle", "UK");
        Swimmer swimmer3 = new Swimmer(3, "Andrej", "Barna", "Serbia");
        Swimmer swimmer4 = new Swimmer(4, "Caleb", "Dressel", "USA");
        Swimmer swimmer5 = new Swimmer(5, "Zhanle", "Pan", "China");
        Swimmer swimmer6 = new Swimmer(6, "David", "Popovici", "Romania");
        Swimmer swimmer7 = new Swimmer(7, "Maxime", "Grousset", "France");
        Swimmer swimmer8 = new Swimmer(8, "Joshua", "Liendo Edwards", "Canada");
        Race semifinal1 = new Race(swimmer1, swimmer2, swimmer3, swimmer4, "48.11", "48.05", "47.71", "48.66");
        Race semifinal2 = new Race(swimmer5, swimmer6, swimmer7, swimmer8, "47.63", "47.02", "47.29", "47.86");
        List<Race> semifinals = List.of(semifinal1, semifinal2);
        when(raceRepository.getRaceByEventAndRound(event, roundType)).thenReturn(finals);
        when(raceRepository.getRaceByEventAndRound(event, RoundType.Semifinal)).thenReturn(semifinals);
        when(raceRepository.saveAll(finals)).thenReturn(finals);

        List<Race> result = raceService.generateFinalStage(eventId, roundType);

        assertNotNull(result);
        assertEquals(semifinal1.getSwimmerLane3(), result.get(0).getSwimmerLane4());
        assertEquals(semifinal2.getSwimmerLane1(), result.get(0).getSwimmerLane1());
        assertEquals(semifinal2.getSwimmerLane3(), result.get(0).getSwimmerLane3());
        assertEquals(semifinal2.getSwimmerLane2(), result.get(0).getSwimmerLane2());

        verify(eventService).getEventById(eventId);
        verify(raceRepository).getRaceByEventAndRound(event, roundType);
        verify(raceRepository).getRaceByEventAndRound(event, RoundType.Semifinal);
        verify(raceRepository).saveAll(finals);
    }


    @Test
    @DisplayName("Sort swimmers by time")
    void sortSwimmersByTimeTest(){
        int swimmer1Id = 1;
        int swimmer2Id = 2;
        int swimmer3Id = 3;
        Swimmer swimmer1 = new Swimmer();
        swimmer1.setId(swimmer1Id);
        Swimmer swimmer2 = new Swimmer();
        swimmer2.setId(swimmer2Id);
        Swimmer swimmer3 = new Swimmer();
        swimmer3.setId(swimmer3Id);
        Race race = new Race(swimmer1, swimmer2, swimmer3, null, "48.30", "47.08", "47.66", null);
        List<Swimmer> swimmerList = List.of(race.getSwimmerLane2(), race.getSwimmerLane3(), race.getSwimmerLane1());

        List<Swimmer> result = raceService.sortSwimmersByTime(List.of(race));

        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals(swimmerList.get(0), result.get(0));
        assertEquals(swimmerList.get(1), result.get(1));
        assertEquals(swimmerList.get(2), result.get(2));
    }

    @Test
    @DisplayName("Schedule final stage")
    void scheduleFinalStageTest(){
        int eventId = 1;
        Event event = new Event();
        event.setId(eventId);
        int sessionId = 1;
        Session session = new Session();
        session.setId(sessionId);
        FinalStageRequest finalStageRequest = new FinalStageRequest("Semifinal 1", RoundType.Semifinal, eventId, sessionId, "18:55");
        Race race = new Race("Semifinal 1", RoundType.Semifinal, "18:55");
        when(eventService.getEventById(eventId)).thenReturn(event);
        when(sessionService.getSessionById(sessionId)).thenReturn(session);
        when(raceRequestMapper.finalStageToRace(finalStageRequest)).thenReturn(race);
        when(raceRepository.save(race)).thenReturn(race);

        Race result = raceService.scheduleFinalStage(finalStageRequest);

        assertNotNull(result);
        assertEquals(finalStageRequest.getName(), result.getName());
        assertEquals(finalStageRequest.getRound(), result.getRound());
        assertEquals(finalStageRequest.getStartTime(), result.getStartTime());
        assertEquals(finalStageRequest.getEventId(), result.getEvent().getId());
        assertEquals(finalStageRequest.getSessionId(), result.getSession().getId());

        verify(eventService).getEventById(eventId);
        verify(sessionService).getSessionById(sessionId);
        verify(raceRepository).save(race);
    }

    @Test
    @DisplayName("Choose swimmer to replace withdrawn swimmer")
    void chooseSwimmerToReplaceTest(){
        Swimmer swimmer1 = new Swimmer(1, "Lorenzo", "Zazzeri", "Italy");
        Swimmer swimmer2 = new Swimmer(2, "Jacob Henry", "Whittle", "UK");
        Swimmer swimmer3 = new Swimmer(3, "Andrej", "Barna", "Serbia");
        Swimmer swimmer4 = new Swimmer(4, "Caleb", "Dressel", "USA");
        Swimmer swimmer5 = new Swimmer(5, "Zhanle", "Pan", "China");
        Swimmer swimmer6 = new Swimmer(6, "David", "Popovici", "Romania");
        Swimmer swimmer7 = new Swimmer(7, "Maxime", "Grousset", "France");
        Swimmer swimmer8 = new Swimmer(8, "Joshua", "Liendo Edwards", "Canada");
        Race semifinal1 = new Race(swimmer1, swimmer2, swimmer3, swimmer4, "48.11", "48.05", "47.71", "48.66");
        Race semifinal2 = new Race(swimmer5, swimmer6, swimmer7, swimmer8, "47.63", "47.02", "47.29", "47.86");
        List<Race> semifinals = List.of(semifinal1, semifinal2);
        Race finalRace = new Race(swimmer5, swimmer6, swimmer7, swimmer8);
        List<Race> finals = List.of(finalRace);
        int swimmerId = 8;
        int nrSwimmers = 4;

        Race result = raceService.chooseSwimmerToReplace(semifinals, finals, swimmerId, nrSwimmers, semifinals.get(1));

        assertEquals(result.getSwimmerLane4(), semifinal1.getSwimmerLane2());
    }

    @Test
    @DisplayName("Replace swimmer id not found")
    void replaceSwimmerExceptionTest(){
        Swimmer swimmer1 = new Swimmer();
        swimmer1.setId(1);
        Swimmer swimmer2 = new Swimmer();
        swimmer1.setId(2);
        Swimmer swimmer3 = new Swimmer();
        swimmer1.setId(3);
        Swimmer swimmer4 = new Swimmer();
        swimmer1.setId(4);
        Race race = new Race(swimmer1, swimmer2, swimmer3, swimmer4);
        int raceId = 1;
        int swimmerId = 5;
        when(raceRepository.findById(raceId)).thenReturn(Optional.of(race));

        SwimmerIdNotFoundException exception = assertThrows(SwimmerIdNotFoundException.class,
                () -> raceService.replaceSwimmer(raceId, swimmerId));

        assertNotNull(exception);
        assertEquals("No swimmer with id 5 in race with id 1", exception.getMessage());

        verify(raceRepository).findById(raceId);
    }

    @Test
    @DisplayName("Replace swimmer from semifinal")
    void replaceSwimmerFromSemifinalTest(){
        Event event = new Event(StrokeType.Freestyle, 100, true);
        Swimmer swimmer1 = new Swimmer(1, "Mikel", "Schreuders", "Aruba");
        Swimmer swimmer2 = new Swimmer(2, "Dylan", "Carter", "Trinidad and Tobago");
        Swimmer swimmer3 = new Swimmer(3, "Brooks", "Curry", "USA");
        Swimmer swimmer4 = new Swimmer(4, "Nandor", "Nemeth", "Hungary");
        Swimmer swimmer5 = new Swimmer(5, "Lorenzo", "Zazzeri", "Italy");
        Swimmer swimmer6 = new Swimmer(6, "Jacob Henry", "Whittle", "UK");
        Swimmer swimmer7 = new Swimmer(7, "Andrej", "Barna", "Serbia");
        Swimmer swimmer8 = new Swimmer(8, "Caleb", "Dressel", "USA");
        Swimmer swimmer9 = new Swimmer(9, "Zhanle", "Pan", "China");
        Swimmer swimmer10 = new Swimmer(10, "David", "Popovici", "Romania");
        Swimmer swimmer11 = new Swimmer(11, "Maxime", "Grousset", "France");
        Swimmer swimmer12 = new Swimmer(12, "Joshua", "Liendo Edwards", "Canada");
        Race heat1 = new Race(swimmer1, swimmer2, swimmer3, swimmer4, "49.11", "48.88", "49.55", "48.90");
        Race heat2 = new Race(swimmer5, swimmer6, swimmer7, swimmer8, "48.71", "48.23", "49.02", "48.11");
        Race heat3 = new Race(swimmer9, swimmer10, swimmer11, swimmer12, "47.99", "47.39", "47.74", "48.01");
        List<Race> heats = List.of(heat1, heat2, heat3);
        Race semifinal1 = new Race(swimmer6, swimmer11, swimmer12, swimmer2);
        semifinal1.setEvent(event);
        semifinal1.setRound(RoundType.Semifinal);
        Race semifinal2 = new Race(swimmer8, swimmer10, swimmer9, swimmer5);
        semifinal2.setEvent(event);
        semifinal2.setRound(RoundType.Semifinal);
        List<Race> semifinals = List.of(semifinal1, semifinal2);
        int raceId = 1;
        int swimmerId = 6;
        when(raceRepository.findById(raceId)).thenReturn(Optional.of(semifinal1));
        when(raceRepository.getRaceByEventAndRound(event, RoundType.Heat)).thenReturn(heats);
        when(raceRepository.getRaceByEventAndRound(event, RoundType.Semifinal)).thenReturn(semifinals);
        when(raceRepository.save(semifinal1)).thenReturn(semifinal1);

        Race result = raceService.replaceSwimmer(raceId, swimmerId);

        assertNotNull(result);
        assertEquals(swimmer4, result.getSwimmerLane1());

        verify(raceRepository).findById(raceId);
        verify(raceRepository).getRaceByEventAndRound(event, RoundType.Heat);
        verify(raceRepository).getRaceByEventAndRound(event, RoundType.Semifinal);
        verify(raceRepository).save(semifinal1);
    }

    @Test
    @DisplayName("Replace swimmer from final based on semifinals")
    void replaceSwimmerFromFinalOnSemifinalsTest(){
        Event event = new Event(StrokeType.Freestyle, 100, true);
        Swimmer swimmer2 = new Swimmer(2, "Dylan", "Carter", "Trinidad and Tobago");
        Swimmer swimmer5 = new Swimmer(5, "Lorenzo", "Zazzeri", "Italy");
        Swimmer swimmer6 = new Swimmer(6, "Jacob Henry", "Whittle", "UK");
        Swimmer swimmer8 = new Swimmer(8, "Caleb", "Dressel", "USA");
        Swimmer swimmer9 = new Swimmer(9, "Zhanle", "Pan", "China");
        Swimmer swimmer10 = new Swimmer(10, "David", "Popovici", "Romania");
        Swimmer swimmer11 = new Swimmer(11, "Maxime", "Grousset", "France");
        Swimmer swimmer12 = new Swimmer(12, "Joshua", "Liendo Edwards", "Canada");
        Race semifinal1 = new Race(swimmer6, swimmer11, swimmer12, swimmer2, "47.69", "47.55", "48.00", "48.04");
        Race semifinal2 = new Race(swimmer8, swimmer10, swimmer9, swimmer5, "47.82", "46.98", "47.23", "47.64");
        List<Race> semifinals = List.of(semifinal1, semifinal2);
        Race finalRace = new Race(swimmer11, swimmer10, swimmer9, swimmer5);
        finalRace.setRound(RoundType.Final);
        finalRace.setEvent(event);
        List<Race> finals = List.of(finalRace);
        int raceId = 1;
        int swimmerId = 9;
        when(raceRepository.findById(raceId)).thenReturn(Optional.of(finalRace));
        when(raceRepository.getRaceByEventAndRound(event, RoundType.Semifinal)).thenReturn(semifinals);
        when(raceRepository.getRaceByEventAndRound(event, RoundType.Final)).thenReturn(finals);
        when(raceRepository.save(finalRace)).thenReturn(finalRace);

        Race result = raceService.replaceSwimmer(raceId, swimmerId);

        assertNotNull(result);
        assertEquals(swimmer6, result.getSwimmerLane3());

        verify(raceRepository).findById(raceId);
        verify(raceRepository).getRaceByEventAndRound(event, RoundType.Semifinal);
        verify(raceRepository).getRaceByEventAndRound(event, RoundType.Final);
        verify(raceRepository).save(finalRace);
    }

    @Test
    @DisplayName("Replace swimmer from final based on heats")
    void replaceSwimmerFromFinalOnHeatsTest(){
        Event event = new Event(StrokeType.Freestyle, 1500, false);
        Swimmer swimmer1 = new Swimmer(1, "Florian", "Wellbrock", "Germany");
        Swimmer swimmer2 = new Swimmer(2, "Mykhailo", "Romanchuk", "Ukraine");
        Swimmer swimmer3 = new Swimmer(3, "Bobby", "Finke", "USA");
        Swimmer swimmer4 = new Swimmer(4, "Guilherme", "Costa", "Brazil");
        Swimmer swimmer5 = new Swimmer(5, "Damien", "Joly", "France");
        Swimmer swimmer6 = new Swimmer(6, "Gregorio", "Paltrinieri", "Italy");
        Swimmer swimmer7 = new Swimmer(7, "Daniel", "Jervis", "UK");
        Swimmer swimmer8 = new Swimmer(8, "Daniel", "Wiffen", "Ireland");
        Race heat1 = new Race(swimmer1, swimmer2, swimmer3, swimmer4, "15:00.33", "14:50.12", "14:50.68", "15:07.70");
        Race heat2 = new Race(swimmer5, swimmer6, swimmer7, swimmer8, "14:53.59", "14:50.71", "14:54.56", "14:57.66");
        List<Race> heats = List.of(heat1, heat2);
        Race finalRace = new Race(swimmer6, swimmer2, swimmer3, swimmer5);
        finalRace.setRound(RoundType.Final);
        finalRace.setEvent(event);
        List<Race> finals = List.of(finalRace);
        int raceId = 1;
        int swimmerId = 2;
        when(raceRepository.findById(raceId)).thenReturn(Optional.of(finalRace));
        when(raceRepository.getRaceByEventAndRound(event, RoundType.Heat)).thenReturn(heats);
        when(raceRepository.getRaceByEventAndRound(event, RoundType.Final)).thenReturn(finals);
        when(raceRepository.save(finalRace)).thenReturn(finalRace);

        Race result = raceService.replaceSwimmer(raceId, swimmerId);

        assertNotNull(result);
        assertEquals(swimmer7, result.getSwimmerLane2());

        verify(raceRepository).findById(raceId);
        verify(raceRepository).getRaceByEventAndRound(event, RoundType.Heat);
        verify(raceRepository).getRaceByEventAndRound(event, RoundType.Final);
        verify(raceRepository).save(finalRace);
    }


}