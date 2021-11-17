package com.springbank.user.query.api.handlers;

import com.springbank.user.core.models.User;
import com.springbank.user.query.api.dto.UserResponse;
import com.springbank.user.query.api.queries.FindAllUsersQuery;
import com.springbank.user.query.api.queries.FindUserByIdQuery;
import com.springbank.user.query.api.queries.SearchUsersQuery;
import com.springbank.user.query.api.repositories.UserRepository;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class UserQueryHandlersImpl implements UserQueryHandler{

    @Autowired
    private UserRepository userRepository;


    @Override
    @QueryHandler
    public UserResponse getAllUsers(FindAllUsersQuery findAllUsersQuery) {
        List<User> users= userRepository.findAll();
        return new UserResponse(users);
    }

    @Override
    @QueryHandler
    public UserResponse getUserById(FindUserByIdQuery findUserByIdQuery) {
        Optional<User> userOptional = userRepository.findById(findUserByIdQuery.getId());
        return userOptional.isPresent()?new UserResponse(Arrays.asList(userOptional.get())):null;
    }

    @Override
    @QueryHandler
    public UserResponse searchUsers(SearchUsersQuery searchUsersQuery) {
        List<User> users= userRepository.findUserByFilter(searchUsersQuery.getFilter());
        System.out.println("printing logger..."+users +"searchUsersQuery---"+searchUsersQuery.toString());
        return new UserResponse(users);
    }
}
