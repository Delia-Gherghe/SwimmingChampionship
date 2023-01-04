package com.example.swimmingchampionship.controller;

import com.example.swimmingchampionship.dto.SwimmerRequest;
import com.example.swimmingchampionship.model.Swimmer;
import com.example.swimmingchampionship.service.SwimmerService;
import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/swimmer")
@Api(value = "/swimmer", tags = "Swimmers")
public class SwimmerController {
    private final SwimmerService swimmerService;

    public SwimmerController(SwimmerService swimmerService) {
        this.swimmerService = swimmerService;
    }

    @PostMapping
    @ApiOperation(value = "Create a swimmer", notes = "Creates a new Swimmer based on the information received in the request")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "The Swimmer was successfully created based on the received request"),
            @ApiResponse(code = 400, message = "Validation error on the received request")
    })
    public ResponseEntity<Swimmer> addNewSwimmer(@RequestBody @Valid SwimmerRequest swimmerRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(swimmerService.saveSwimmer(swimmerRequest));
    }

    @GetMapping
    @ApiOperation(value = "Show swimmers", notes = "Displays all the information about swimmers (optionally filtered by country)")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The swimmers were successfully retrieved")
    })
    public ResponseEntity<List<Swimmer>> getSwimmers(@RequestParam(required = false) @ApiParam(value = "The country to filter by") String country){
        return ResponseEntity.ok().body(swimmerService.getSwimmers(country));
    }
}
