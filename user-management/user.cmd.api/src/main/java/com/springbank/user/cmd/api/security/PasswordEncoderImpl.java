package com.springbank.user.cmd.api.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PasswordEncoderImpl implements PasswordEncoder{
    @Override
    public String hashPassword(String password) {
        return new BCryptPasswordEncoder(12).encode(password);
    }
}
