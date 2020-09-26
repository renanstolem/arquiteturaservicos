package com.netflix.users.controllers;

import com.netflix.users.dto.TicketDTO;
import com.netflix.users.responses.StandardResponseTicket;
import com.netflix.users.services.UserService;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@Api(value = "User's API",tags = { "Users" })
@RequestMapping(produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
@AllArgsConstructor
@ApiResponses({
        @ApiResponse(code = 400, message = "Bad Request"),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 404, message = "Not Found"),
        @ApiResponse(code = 422, message = "Unprocessable Entity"),
        @ApiResponse(code = 500, message = "Internal Server Error") })
public class UserController {

    @Autowired
    UserService service;

    @ApiOperation("Create a ticket")
    @ApiResponse(code = 201, message = "OK", response = StandardResponseTicket.class)
    @PostMapping(value = "/users/{id}/ticket", consumes = "application/json", produces = "application/json")
    @ResponseBody
    public ResponseEntity<StandardResponseTicket> saveCustomer(
            @PathVariable("id") @ApiParam(required = true) Integer id,
            @RequestBody @ApiParam(name = "Ticket", value = "Ticket") TicketDTO ticketDTO) {
        return service.saveTicket(id, ticketDTO);
    }
}
