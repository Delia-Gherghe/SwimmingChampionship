package com.example.swimmingchampionship.controller;

import com.example.swimmingchampionship.dto.FinalStageRequest;
import com.example.swimmingchampionship.dto.HeatRequest;
import com.example.swimmingchampionship.dto.TimesDto;
import com.example.swimmingchampionship.model.Race;
import com.example.swimmingchampionship.model.RoundType;
import com.example.swimmingchampionship.service.RaceService;
import com.example.swimmingchampionship.validation.FinalStage;
import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/race")
@Validated
@Api(value = "/race", tags = "Races")
public class RaceController {
    private final RaceService raceService;

    public RaceController(RaceService raceService) {
        this.raceService = raceService;
    }

    @GetMapping("/{raceId}")
    @ApiOperation(value = "Show race", notes = "Displays all the information about a race based on the given id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The race was successfully retrieved"),
            @ApiResponse(code = 404, message = "Race with given id not found")
    })
    public ResponseEntity<Race> getRaceById(@PathVariable @ApiParam(value = "The id of the race", required = true) int raceId){
        return ResponseEntity.ok().body(raceService.getRaceById(raceId));
    }

    @PutMapping("/{raceId}")
    @ApiOperation(value = "Update times", notes = "Sets the times of a race based on the information received in the request")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "The times were successfully updated based on the received request"),
            @ApiResponse(code = 400, message = "Validation error on the received request"),
            @ApiResponse(code = 404, message = "Race with given id not found")
    })
    public ResponseEntity<Race> updateTimes(@PathVariable @ApiParam(value = "The id of the race", required = true) int raceId,
                                            @RequestBody @Valid TimesDto times){
        return ResponseEntity.status(HttpStatus.CREATED).body(raceService.updateTimes(raceId, times));
    }

    @PostMapping("/heat")
    @ApiOperation(value = "Create a Heat", notes = "Creates a new Race from the Heats round based on the information received in the request")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "The Heat was successfully created based on the received request"),
            @ApiResponse(code = 400, message = "Validation error on the received request"),
            @ApiResponse(code = 404, message = "Heat details not found based on the provided ids")
    })
    public ResponseEntity<Race> addHeat(@RequestBody @Valid HeatRequest heatRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(raceService.addHeat(heatRequest));
    }

    @PostMapping("/generate/{eventId}")
    @ApiOperation(value = "Generate final stage", notes = "Places swimmers on the lanes of a final stage based on the ranking from the previous round of the event")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "The swimmers were successfully placed on the lanes"),
            @ApiResponse(code = 400, message = "Invalid final round name"),
            @ApiResponse(code = 404, message = "Event with given id not found or insufficient existing information to place the swimmers")
    })
    public ResponseEntity<List<Race>> generateFinalStage(@PathVariable @ApiParam(value = "The id of the event", required = true) int eventId,
                                                         @ApiParam(value = "The final round name (Semifinal/Final)", required = true)
                                                         @RequestParam @FinalStage RoundType round){
        return ResponseEntity.status(HttpStatus.CREATED).body(raceService.generateFinalStage(eventId, round));
    }

    @PostMapping("/finalStage")
    @ApiOperation(value = "Create a final stage", notes = "Creates a new Race from the Semifinal/Final round based on the information received in the request")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "The final stage was successfully created based on the received request"),
            @ApiResponse(code = 400, message = "Validation error on the received request"),
            @ApiResponse(code = 404, message = "Race details not found based on the provided ids")
    })
    public ResponseEntity<Race> scheduleFinalStage(@RequestBody @Valid FinalStageRequest finalStageRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(raceService.scheduleFinalStage(finalStageRequest));
    }

    @PutMapping("/{raceId}/withdraw")
    @ApiOperation(value = "Replace withdrawn swimmer", notes = "Replaces the swimmer with the given id from the given race with the next best swimmer from the previous round of the event")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "The swimmer was successfully replaced"),
            @ApiResponse(code = 404, message = "Race or swimmer not found based on the provided ids")
    })
    public ResponseEntity<Race> replaceWithdrawnSwimmer(@ApiParam(value = "The id of the race", required = true) @PathVariable int raceId,
                                                        @ApiParam(value = "The id of the swimmer to be replaced", required = true)
                                                        @RequestParam int swimmerId){
        return ResponseEntity.status(HttpStatus.CREATED).body(raceService.replaceSwimmer(raceId, swimmerId));
    }

}
