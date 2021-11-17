package com.springbank.user.cmd.api.controllers;

import com.springbank.user.cmd.api.commands.RegisterUserCommand;
import com.springbank.user.cmd.api.dto.RegisterUserResponse;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/registerUser")
public class RegisterUserController {

    private CommandGateway commandGateway;

    @Autowired
    public RegisterUserController(CommandGateway commandGateway){
        this.commandGateway = commandGateway;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('WRITE_PRIVILAGE')")
    public ResponseEntity<RegisterUserResponse> registerUser(@Valid @RequestBody RegisterUserCommand userCommand){
        userCommand.setId(UUID.randomUUID().toString());
        try{
            commandGateway.send(userCommand);
            return new ResponseEntity<>(new RegisterUserResponse("user registered successfully wit id: ",userCommand.getId()),HttpStatus.CREATED);

        }catch(Exception ex){
            String errorMsg = "Error occurred while registerign user with id: "+userCommand.getId();
            System.out.println("error messgae->"+ex.toString());
            return new ResponseEntity<>(new RegisterUserResponse(errorMsg,userCommand.getId()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
