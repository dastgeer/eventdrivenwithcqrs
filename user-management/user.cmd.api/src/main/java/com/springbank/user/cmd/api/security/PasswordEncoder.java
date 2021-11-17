package com.springbank.user.cmd.api.security;

public interface PasswordEncoder {

    public String hashPassword(String password);
}
