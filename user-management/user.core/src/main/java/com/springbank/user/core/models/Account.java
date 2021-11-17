package com.springbank.user.core.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    @Size(min = 2,message = "username should be min 2 char")
    private String username;

    @Size(min=6, message = "passowrd should be min 6 char")
    private String password;
    @NotNull(message = "role must provide atleast 1")
    private List<Role> roles;
}
