package com.example.swimmingchampionship.controller;

import com.example.swimmingchampionship.model.Sponsor;
import com.example.swimmingchampionship.service.SponsorService;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sponsor")
@Api(value = "/sponsor", tags = "Sponsors")
public class SponsorController {
    private final SponsorService sponsorService;

    public SponsorController(SponsorService sponsorService) {
        this.sponsorService = sponsorService;
    }

    @GetMapping("/{sponsorId}")
    @ApiOperation(value = "Show sponsor", notes = "Displays all the information about a company and the swimmers they sponsor based on the given id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The sponsor was successfully retrieved"),
            @ApiResponse(code = 404, message = "Sponsor with given id not found")
    })
    public ResponseEntity<Sponsor> getSponsorById(@PathVariable @ApiParam(value = "The id of the sponsor", required = true) int sponsorId){
        return ResponseEntity.ok().body(sponsorService.getSponsorById(sponsorId));
    }
}
