package com.springbank.bankacc.cmd.api.controllers;

import com.springbank.bankacc.cmd.api.commands.OpenAccountCommand;
import com.springbank.bankacc.cmd.api.dto.OpenAccountResponse;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping(path ="/api/v1/openAccount")
public class OpenAccountController {

    private CommandGateway commandGateway;

    @Autowired
    public OpenAccountController(CommandGateway commandGateway){
        this.commandGateway = commandGateway;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('WRITE_PRIVILAGE')")
    public ResponseEntity<OpenAccountResponse> createAccount(@Valid @RequestBody OpenAccountCommand openAccountCommand){

        String id= UUID.randomUUID().toString();
        openAccountCommand.setId(id);
        try{
            commandGateway.send(openAccountCommand);
            return new ResponseEntity<>(new OpenAccountResponse("opened account successfully ",id), HttpStatus.CREATED);
        }catch (Exception e){
            String errorMessage = "Error Occured while opening account "+id;
            return new ResponseEntity<>(new OpenAccountResponse(errorMessage,id),HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
