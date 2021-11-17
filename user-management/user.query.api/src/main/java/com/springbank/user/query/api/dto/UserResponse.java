package com.springbank.user.query.api.dto;

import com.springbank.user.core.dto.BaseResponse;
import com.springbank.user.core.models.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;


public class UserResponse extends BaseResponse {

    private List<User> user;

    public UserResponse(String message) {
        super(message);
    }

    public UserResponse(List<User> users) {
        super(null);
        this.user = users;
    }

    public UserResponse(String message, List<User> users) {
        super(message);
        this.user= users;
    }


    public List<User> getUser() {
        return user;
    }

    public void setUser(List<User> user) {
        this.user = user;
    }
}
