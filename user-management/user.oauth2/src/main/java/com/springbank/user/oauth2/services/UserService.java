package com.springbank.user.oauth2.services;

import com.springbank.user.core.models.User;
import com.springbank.user.oauth2.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service(value = "userService")
public class UserService implements UserDetailsService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(userName);
        if(!user.isPresent()) {
             throw new UsernameNotFoundException("invalid  username /password supplied.");
        }
        return org.springframework.security.core.userdetails.User.withUsername(user.get().getAccount().getUsername())
                .password(user.get().getAccount().getPassword())
                .authorities(user.get().getAccount().getRoles())
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(false)
                        .build();
    }
}
