package com.springbank.bankacc.query.api.controllers;


import com.springbank.bankacc.query.api.dto.AccountLookUpResponse;
import com.springbank.bankacc.query.api.dto.EqualityType;
import com.springbank.bankacc.query.api.queries.FindAccountByIdQuery;
import com.springbank.bankacc.query.api.queries.FindAccountWithBalanceQuery;
import com.springbank.bankacc.query.api.queries.FindAllAccountsQuery;
import com.springbank.bankacc.query.api.queries.FindByAccountHolderIdQuery;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path ="/api/v1/bankAccountLookup")
public class AccountLookUpController {

    private QueryGateway queryGateway;

    @Autowired
    public AccountLookUpController(QueryGateway queryGateway){
        this.queryGateway = queryGateway;
    }

    @GetMapping(path = "/getAll")
    @PreAuthorize("hasAuthority('READ_PRIVILEGE')")
    public ResponseEntity<AccountLookUpResponse> getAllAccounts(){
        try {
            AccountLookUpResponse allAccountResposne = queryGateway.query(new FindAllAccountsQuery(), ResponseTypes.instanceOf(AccountLookUpResponse.class)).join();
            if(allAccountResposne== null || allAccountResposne.getBankAccounts()== null || allAccountResposne.getBankAccounts().size()==0){
                return new ResponseEntity<>(new AccountLookUpResponse("No record found"), HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(allAccountResposne, HttpStatus.OK);
        }catch(Exception e){
            String errorMessage ="Exception occured while fetching all accounts";
            return new ResponseEntity<>(new AccountLookUpResponse(errorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/byId/{id}")
    @PreAuthorize("hasAuthority('READ_PRIVILEGE')")
    public ResponseEntity<AccountLookUpResponse> getAccountById(@PathVariable String id){
        try {
            AccountLookUpResponse allAccountResposne = queryGateway.query(new FindAccountByIdQuery(id), ResponseTypes.instanceOf(AccountLookUpResponse.class)).join();
            if(allAccountResposne== null || allAccountResposne.getBankAccounts()== null || allAccountResposne.getBankAccounts().size()==0){
                return new ResponseEntity<>(new AccountLookUpResponse("No record found account by Id "+ id), HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(allAccountResposne, HttpStatus.OK);
        }catch(Exception e){
            String errorMessage ="Exception occured while fetching account by Id "+ id;
            return new ResponseEntity<>(new AccountLookUpResponse(errorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping(path = "/byHolderId/{equalityType}/{balance}")
    @PreAuthorize("hasAuthority('READ_PRIVILEGE')")
    public ResponseEntity<AccountLookUpResponse> getAccountsByEqualityType(@PathVariable EqualityType equalityType, @PathVariable double balance){
        try {
            AccountLookUpResponse allAccountResposne = queryGateway.query(new FindAccountWithBalanceQuery(equalityType,balance), ResponseTypes.instanceOf(AccountLookUpResponse.class)).join();
            if(allAccountResposne== null || allAccountResposne.getBankAccounts()== null || allAccountResposne.getBankAccounts().size()==0){
                return new ResponseEntity<>(new AccountLookUpResponse("No record found balance equalityType "+equalityType + "balance"+balance), HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(allAccountResposne, HttpStatus.OK);
        }catch(Exception e){
            String errorMessage ="Exception occured while fetching account by equality & balance "+ equalityType +"balance "+balance;
            return new ResponseEntity<>(new AccountLookUpResponse(errorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/byHolderId/{holderId}")
    @PreAuthorize("hasAuthority('READ_PRIVILEGE')")
    public ResponseEntity<AccountLookUpResponse> getAccountByHolderId(@PathVariable String holderId){
        try {
            AccountLookUpResponse allAccountResposne = queryGateway.query(new FindByAccountHolderIdQuery(holderId), ResponseTypes.instanceOf(AccountLookUpResponse.class)).join();
            if(allAccountResposne== null || allAccountResposne.getBankAccounts()== null || allAccountResposne.getBankAccounts().size()==0){
                return new ResponseEntity<>(new AccountLookUpResponse("No record found holder Id "+ holderId), HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(allAccountResposne, HttpStatus.OK);
        }catch(Exception e){
            String errorMessage ="Exception occured while fetching account by holder Id "+ holderId;
            return new ResponseEntity<>(new AccountLookUpResponse(errorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
