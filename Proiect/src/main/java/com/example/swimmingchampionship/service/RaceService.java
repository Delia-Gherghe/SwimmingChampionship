package com.example.swimmingchampionship.service;

import com.example.swimmingchampionship.dto.FinalStageRequest;
import com.example.swimmingchampionship.dto.HeatRequest;
import com.example.swimmingchampionship.dto.TimesDto;
import com.example.swimmingchampionship.exception.*;
import com.example.swimmingchampionship.mapper.RaceRequestMapper;
import com.example.swimmingchampionship.model.*;
import com.example.swimmingchampionship.repository.RaceRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RaceService {
    private final RaceRepository raceRepository;
    private final EventService eventService;
    private final SessionService sessionService;
    private final SwimmerService swimmerService;
    private final RaceRequestMapper raceRequestMapper;

    public RaceService(RaceRepository raceRepository, EventService eventService, SessionService sessionService, SwimmerService swimmerService, RaceRequestMapper raceRequestMapper) {
        this.raceRepository = raceRepository;
        this.eventService = eventService;
        this.sessionService = sessionService;
        this.swimmerService = swimmerService;
        this.raceRequestMapper = raceRequestMapper;
    }

    public Race getRaceById(int raceId){
        return raceRepository.findById(raceId).orElseThrow(() ->
                new RaceIdNotFoundException("Race with id " + raceId + " does not exist!"));
    }

    public Race updateTimes(int raceId, TimesDto times){
        Race race = getRaceById(raceId);
        race.setTimeLane1(times.getTimeLane1());
        race.setTimeLane2(times.getTimeLane2());
        race.setTimeLane3(times.getTimeLane3());
        race.setTimeLane4(times.getTimeLane4());
        return raceRepository.save(race);
    }

    public Race addHeat(HeatRequest heatRequest){
        Event event = eventService.getEventById(heatRequest.getEventId());
        Session session = sessionService.getSessionById(heatRequest.getSessionId());
        Swimmer swimmer1 = null;
        Swimmer swimmer2 = null;
        Swimmer swimmer3 = null;
        Swimmer swimmer4 = null;
        if (heatRequest.getLane1SwimmerId() != null){
            swimmer1 = swimmerService.getSwimmerById(heatRequest.getLane1SwimmerId());
        }
        if (heatRequest.getLane2SwimmerId() != null){
            swimmer2 = swimmerService.getSwimmerById(heatRequest.getLane2SwimmerId());
        }
        if (heatRequest.getLane3SwimmerId() != null){
            swimmer3 = swimmerService.getSwimmerById(heatRequest.getLane3SwimmerId());
        }
        if (heatRequest.getLane4SwimmerId() != null){
            swimmer4 = swimmerService.getSwimmerById(heatRequest.getLane4SwimmerId());
        }
        Race race = raceRequestMapper.heatRequestToRace(heatRequest);
        race.setEvent(event);
        race.setSession(session);
        race.setSwimmerLane1(swimmer1);
        race.setSwimmerLane2(swimmer2);
        race.setSwimmerLane3(swimmer3);
        race.setSwimmerLane4(swimmer4);
        return raceRepository.save(race);
    }

    public List<Race> generateFinalStage(int eventId, RoundType round){
        Event event = eventService.getEventById(eventId);
        if (round == RoundType.Semifinal){
            if (!event.getHasSemifinal()){
                throw new NoSemifinalException("Event with id " + eventId + " does not have a semifinal round");
            }
            List<Race> semifinals = raceRepository.getRaceByEventAndRoundOrderByStartTime(event, round);
            if (semifinals.size() != 2){
                throw new WrongNumberOfRacesException("Not 2 semifinals scheduled for event with id " + eventId);
            }
            List<Race> heats = raceRepository.getRaceByEventAndRound(event, RoundType.Heat);
            if (heats.size() < 3){
                throw new WrongNumberOfRacesException("Not enough heats for event with id " + eventId);
            }

            List<Swimmer> swimmerList = sortSwimmersByTime(heats);

            if (swimmerList.size() < 8){
                throw new WrongNumberOfSwimmersException("Not enough swimmers finished the heats for event with id " + eventId);
            }

            semifinals.get(0).setSwimmerLane4(swimmerList.get(7));
            semifinals.get(1).setSwimmerLane4(swimmerList.get(6));
            semifinals.get(0).setSwimmerLane1(swimmerList.get(5));
            semifinals.get(1).setSwimmerLane1(swimmerList.get(4));
            semifinals.get(0).setSwimmerLane3(swimmerList.get(3));
            semifinals.get(1).setSwimmerLane3(swimmerList.get(2));
            semifinals.get(0).setSwimmerLane2(swimmerList.get(1));
            semifinals.get(1).setSwimmerLane2(swimmerList.get(0));

            return raceRepository.saveAll(semifinals);
        } else {
            List<Race> finals = raceRepository.getRaceByEventAndRound(event, round);
            if (finals.size() != 1){
                throw new WrongNumberOfRacesException("1 final must be scheduled for event with id " + eventId);
            }
            List<Race> previous;
            if (event.getHasSemifinal()){
                previous = raceRepository.getRaceByEventAndRound(event, RoundType.Semifinal);
            } else {
                previous = raceRepository.getRaceByEventAndRound(event, RoundType.Heat);
            }
            if (previous.size() < 2){
                throw new WrongNumberOfRacesException("Not enough races from previous round for event with id " + eventId);
            }

            List<Swimmer> swimmerList = sortSwimmersByTime(previous);

            if (swimmerList.size() < 4){
                throw new WrongNumberOfSwimmersException("Not enough swimmers finished the previous round for event with id " + eventId);
            }

            finals.get(0).setSwimmerLane4(swimmerList.get(3));
            finals.get(0).setSwimmerLane1(swimmerList.get(2));
            finals.get(0).setSwimmerLane3(swimmerList.get(1));
            finals.get(0).setSwimmerLane2(swimmerList.get(0));

            return raceRepository.saveAll(finals);
        }
    }

    public List<Swimmer> sortSwimmersByTime(List<Race> races){
        Map<Swimmer, String> swimmerTime = new HashMap<>();
        for (Race race : races){
            swimmerTime.put(race.getSwimmerLane1(), race.getTimeLane1());
            swimmerTime.put(race.getSwimmerLane2(), race.getTimeLane2());
            swimmerTime.put(race.getSwimmerLane3(), race.getTimeLane3());
            swimmerTime.put(race.getSwimmerLane4(), race.getTimeLane4());
        }

        LinkedHashMap<Swimmer, String> sortedSwimmerTimes = new LinkedHashMap<>();

        swimmerTime.entrySet()
                .stream()
                .filter(entry -> entry.getValue() != null)
                .sorted(Map.Entry.comparingByValue())
                .forEachOrdered(x -> sortedSwimmerTimes.put(x.getKey(), x.getValue()));

        return new ArrayList<>(sortedSwimmerTimes.keySet());
    }

    public Race scheduleFinalStage(FinalStageRequest finalStageRequest){
        Event event = eventService.getEventById(finalStageRequest.getEventId());
        Session session = sessionService.getSessionById(finalStageRequest.getSessionId());
        Race race = raceRequestMapper.finalStageToRace(finalStageRequest);
        race.setSession(session);
        race.setEvent(event);
        return raceRepository.save(race);
    }

    public Race replaceSwimmer(int raceId, int swimmerId){
        Race race = getRaceById(raceId);
        if (race.getSwimmerLane1().getId() != swimmerId && race.getSwimmerLane2().getId() != swimmerId &&
                race.getSwimmerLane3().getId() != swimmerId && race.getSwimmerLane4().getId() != swimmerId){
            throw new SwimmerIdNotFoundException("No swimmer with id " + swimmerId + " in race with id " + raceId);
        }
        if (race.getRound() == RoundType.Semifinal){
            List<Race> heats = raceRepository.getRaceByEventAndRound(race.getEvent(), RoundType.Heat);
            List<Race> semifinals = raceRepository.getRaceByEventAndRound(race.getEvent(), RoundType.Semifinal);

            return raceRepository.save(chooseSwimmerToReplace(heats, semifinals, swimmerId, 8, race));
        } else {
            List<Race> previous;
            if (race.getEvent().getHasSemifinal()){
                previous = raceRepository.getRaceByEventAndRound(race.getEvent(), RoundType.Semifinal);
            } else {
                previous = raceRepository.getRaceByEventAndRound(race.getEvent(), RoundType.Heat);
            }
            List<Race> finals = raceRepository.getRaceByEventAndRound(race.getEvent(), RoundType.Final);
            return raceRepository.save(chooseSwimmerToReplace(previous, finals, swimmerId, 4, race));
        }
    }

    public Race chooseSwimmerToReplace(List<Race> previousRound, List<Race> currentRound,
                                       int swimmerId, int nrSwimmers, Race race){
        List<Swimmer> previousRoundSummary = sortSwimmersByTime(previousRound);
        List<Swimmer> swimmersCurrentRound = new ArrayList<>();

        for (Race r : currentRound){
            swimmersCurrentRound.add(r.getSwimmerLane1());
            swimmersCurrentRound.add(r.getSwimmerLane2());
            swimmersCurrentRound.add(r.getSwimmerLane3());
            swimmersCurrentRound.add(r.getSwimmerLane4());
        }

        int nr = 0;

        for (Swimmer s : previousRoundSummary.subList(0, nrSwimmers)){
            if (!swimmersCurrentRound.contains(s)){
                nr += 1;
            }
        }

        Swimmer toReplace = previousRoundSummary.get(nrSwimmers + nr);

        if (race.getSwimmerLane1().getId() == swimmerId){
            race.setSwimmerLane1(toReplace);
        } else if (race.getSwimmerLane2().getId() == swimmerId){
            race.setSwimmerLane2(toReplace);
        } else if (race.getSwimmerLane3().getId() == swimmerId){
            race.setSwimmerLane3(toReplace);
        } else {
            race.setSwimmerLane4(toReplace);
        }

        return race;
    }
}
