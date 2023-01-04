package com.example.swimmingchampionship.controller;

import com.example.swimmingchampionship.dto.SessionDto;
import com.example.swimmingchampionship.service.SessionService;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/session")
@Api(value = "/session", tags = "Sessions")
public class SessionController {
    private final SessionService sessionService;

    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @GetMapping("/{sessionId}/schedule")
    @ApiOperation(value = "Show session schedule", notes = "Displays the session based on the given id with races ordered by start time")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The session schedule was successfully retrieved"),
            @ApiResponse(code = 404, message = "Session with given id not found")
    })
    public ResponseEntity<SessionDto> getSessionById(@PathVariable @ApiParam(value = "The id of the session", required = true) int sessionId){
        return ResponseEntity.ok().body(sessionService.getSessionDtoById(sessionId));
    }
}
