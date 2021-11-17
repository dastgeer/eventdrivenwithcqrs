package com.springbank.user.cmd.api.controllers;

import com.springbank.user.cmd.api.commands.UpdateUserCommand;
import com.springbank.user.core.dto.BaseResponse;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path="/api/v1/updateUser")
public class UpdateUserController {

    private CommandGateway commandGateway;

    @Autowired
    public UpdateUserController(CommandGateway commandGateway){
        this.commandGateway=commandGateway;
    }

    @PutMapping(path = "/{id}")
    @PreAuthorize("hasAuthority('WRITE_PRIVILAGE')")
    public ResponseEntity<BaseResponse> updateUser(@PathVariable String id, @Valid @RequestBody UpdateUserCommand updateUserCommand){
        updateUserCommand.setId(id);
        try{

            commandGateway.send(updateUserCommand);
            return new ResponseEntity<>(new BaseResponse("user updated successfully wit id: "+updateUserCommand.getId()),HttpStatus.CREATED);

        }catch(Exception ex){
            String errorMsg = "Error occurred while update user with id: "+updateUserCommand.getId();
            System.out.println("error messgae->"+ex.toString());
            return new ResponseEntity<>(new BaseResponse(errorMsg), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
