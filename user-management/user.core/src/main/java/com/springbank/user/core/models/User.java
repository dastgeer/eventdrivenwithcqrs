package com.springbank.user.core.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection ="users")
public class User {

    @Id
    private String id;
    @NotEmpty(message = "first name is mandatory")
    private String firstname;

    @NotEmpty(message = "lastname is mandatory")
    private String lastname;
    @Email(message = "email id should be valid")
    private String emailAddress;
    @NotNull(message = "please provide account credential")
    @Valid
    private Account account;
}
