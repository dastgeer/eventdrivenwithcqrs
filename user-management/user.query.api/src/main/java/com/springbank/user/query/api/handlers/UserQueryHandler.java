package com.springbank.user.query.api.handlers;

import com.springbank.user.query.api.dto.UserResponse;
import com.springbank.user.query.api.queries.FindAllUsersQuery;
import com.springbank.user.query.api.queries.FindUserByIdQuery;
import com.springbank.user.query.api.queries.SearchUsersQuery;

public interface UserQueryHandler {

    public UserResponse getAllUsers(FindAllUsersQuery findAllUsersQuery);

    public UserResponse getUserById(FindUserByIdQuery findUserByIdQuery);

    public UserResponse searchUsers(SearchUsersQuery searchUsersQuery);
}
