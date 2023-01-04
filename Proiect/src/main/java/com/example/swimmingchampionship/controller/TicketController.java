package com.example.swimmingchampionship.controller;

import com.example.swimmingchampionship.service.TicketService;
import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.example.swimmingchampionship.model.Ticket;

import javax.validation.constraints.Min;

@RestController
@RequestMapping("/ticket")
@Validated
@Api(value = "/ticket", tags = "Tickets")
public class TicketController {
    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PostMapping("/buy/{userId}/{sessionId}")
    @ApiOperation(value = "Buy tickets", notes = "Allows a user to buy one or more tickets to the session given by the id")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "The tickets were successfully bought based on the received request"),
            @ApiResponse(code = 400, message = "Invalid quantity request"),
            @ApiResponse(code = 404, message = "User or session with given id not found")
    })
    public ResponseEntity<Ticket> buyTickets(@PathVariable @ApiParam(value = "The id of the user", required = true) int userId,
                                                @PathVariable @ApiParam(value = "The id of the session", required = true) int sessionId,
                                                @Min(value = 1, message = "Minimum quantity is 1!")
                                                @RequestParam @ApiParam(value = "The number of tickets", required = true)
                                                int quantity){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ticketService.buyTickets(userId, sessionId, quantity));
    }
}
