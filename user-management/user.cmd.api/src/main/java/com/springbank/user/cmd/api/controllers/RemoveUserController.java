package com.springbank.user.cmd.api.controllers;

import com.springbank.user.cmd.api.commands.RemoveUserCommand;
import com.springbank.user.core.dto.BaseResponse;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/removeUser")
public class RemoveUserController {

    private CommandGateway commandGateway;

    @Autowired
    public RemoveUserController(CommandGateway commandGateway){
        this.commandGateway = commandGateway;
    }


    @DeleteMapping(path = "/{id}")
    @PreAuthorize("hasAuthority('WRITE_PRIVILAGE')")
    public ResponseEntity<BaseResponse> deleteUser(@PathVariable String id){
        try{
            commandGateway.send(new RemoveUserCommand(id));
            return new ResponseEntity<>(new BaseResponse("user removed successfully wit id: "+id), HttpStatus.CREATED);

        }catch(Exception ex){
            String errorMsg = "Error occurred while removed user with id: "+id;
            System.out.println("error messgae->"+ex.toString());
            return new ResponseEntity<>(new BaseResponse(errorMsg), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
