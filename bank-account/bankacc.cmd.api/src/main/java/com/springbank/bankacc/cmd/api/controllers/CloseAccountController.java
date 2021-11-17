package com.springbank.bankacc.cmd.api.controllers;

import com.springbank.bankacc.cmd.api.commands.CloseAccountCommand;
import com.springbank.bankacc.core.dto.BaseResponse;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path ="/api/v1/closeAccount")
public class CloseAccountController {

    private CommandGateway commandGateway;

    @Autowired
    public CloseAccountController(CommandGateway commandGateway){
        this.commandGateway = commandGateway;
    }

    @DeleteMapping(path ="/{id}")
    @PreAuthorize("hasAuthority('WRITE_PRIVILAGE')")
    public ResponseEntity<BaseResponse> deleteAccount(@PathVariable String id){

        try{
            CloseAccountCommand closeCmd= CloseAccountCommand.builder().id(id).build();
            commandGateway.send(closeCmd);
            return new ResponseEntity<>(new BaseResponse(" account deleted successfully "+id), HttpStatus.OK);
        }catch (Exception e){
            String errorMessage = "Error Occured while deleteing account "+id;
            return new ResponseEntity<>(new BaseResponse(errorMessage),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
