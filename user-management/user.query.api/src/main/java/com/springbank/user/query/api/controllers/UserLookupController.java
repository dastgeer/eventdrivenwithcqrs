package com.springbank.user.query.api.controllers;

import com.springbank.user.query.api.dto.UserResponse;
import com.springbank.user.query.api.queries.FindAllUsersQuery;
import com.springbank.user.query.api.queries.FindUserByIdQuery;
import com.springbank.user.query.api.queries.SearchUsersQuery;
import org.axonframework.messaging.responsetypes.ResponseType;
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
@RequestMapping(path = "/api/v1/userLookup")
public class UserLookupController {

    private QueryGateway queryGateway;

    @Autowired
    public UserLookupController(QueryGateway queryGateway){
        this.queryGateway = queryGateway;
    }

    @GetMapping(path = "/")
    @PreAuthorize("hasAuthority('READ_PRIVILEGE')")
    public ResponseEntity<UserResponse> getAllUsers(){

        try{
            FindAllUsersQuery userByQuery= new FindAllUsersQuery();
            UserResponse response = queryGateway.query(userByQuery, ResponseTypes.instanceOf(UserResponse.class)).join();
            if(response==null || response.getUser()==null){
                return new ResponseEntity<>(new UserResponse(null,null), HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(new UserResponse(response.getUser()),HttpStatus.OK);
        }catch(Exception e){
            String errormsg= "failed to get all users";
            return new ResponseEntity<>(new UserResponse(errormsg), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/getById/{id}")
    @PreAuthorize("hasAuthority('READ_PRIVILEGE')")
    public ResponseEntity<UserResponse> getAllUsers(@PathVariable String id ){

        try{
            FindUserByIdQuery userByIdQuery= new FindUserByIdQuery(id);
            UserResponse response = queryGateway.query(userByIdQuery, ResponseTypes.instanceOf(UserResponse.class)).join();
            if(response==null || response.getUser()==null){
                return new ResponseEntity<>(new UserResponse(null,null), HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(new UserResponse(response.getUser()),HttpStatus.OK);
        }catch(Exception e){
            String errormsg= "failed to get all users";
            return new ResponseEntity<>(new UserResponse(errormsg), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/filter/{filterKey}")
    @PreAuthorize("hasAuthority('READ_PRIVILEGE')")
    public ResponseEntity<UserResponse> getUsersByFilterKey(@PathVariable String filterKey ){

        try{
            SearchUsersQuery usersByFilterQuery= new SearchUsersQuery(filterKey);
            UserResponse response = queryGateway.query(usersByFilterQuery, ResponseTypes.instanceOf(UserResponse.class)).join();
            if(response==null || response.getUser()==null){
                return new ResponseEntity<>(new UserResponse(null,null), HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(new UserResponse(response.getUser()),HttpStatus.OK);
        }catch(Exception e){
            String errormsg= "failed to get all users";
            return new ResponseEntity<>(new UserResponse(errormsg), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
