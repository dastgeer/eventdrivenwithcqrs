package com.springbank.bankacc.cmd.api.controllers;

import com.springbank.bankacc.cmd.api.commands.DepositFundCommand;
import com.springbank.bankacc.cmd.api.commands.WithdrawFundCommand;
import com.springbank.bankacc.core.dto.BaseResponse;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path ="/api/v1/withdrawAmount")
public class WithdrawAmountController {

    private CommandGateway commandGateway;

    @Autowired
    public WithdrawAmountController(CommandGateway commandGateway){
        this.commandGateway = commandGateway;
    }

    @PutMapping(path ="/{id}")
    @PreAuthorize("hasAuthority('WRITE_PRIVILAGE')")
    public ResponseEntity<BaseResponse> withDrawAmount(@PathVariable String id, @Valid @RequestBody WithdrawFundCommand withdrawFundCommand){

        try{
            withdrawFundCommand.setId(id);
            commandGateway.send(withdrawFundCommand);
            return new ResponseEntity<>(new BaseResponse("withdraw amount from  account successfully "+id), HttpStatus.OK);
        }catch (Exception e){
            String errorMessage = "Error Occured while withdraw updating account "+id;
            return new ResponseEntity<>(new BaseResponse(errorMessage),HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}