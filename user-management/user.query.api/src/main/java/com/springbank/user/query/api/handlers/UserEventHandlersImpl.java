package com.springbank.user.query.api.handlers;

import com.springbank.user.core.events.UserRegisteredEvent;
import com.springbank.user.core.events.UserRemovedEvent;
import com.springbank.user.core.events.UserUpdatedEvent;
import com.springbank.user.query.api.repositories.UserRepository;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@ProcessingGroup("user-group")
public class UserEventHandlersImpl implements UserEventHandlers {

    private UserRepository userRepository;

    @Autowired
    public UserEventHandlersImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }


    @Override
    @EventHandler
    public void onUserRegisterEvent(UserRegisteredEvent event) {
        userRepository.save(event.getUser());
    }

    @Override
    @EventHandler
    public void onUserUpdatedEvent(UserUpdatedEvent event) {
        userRepository.save(event.getUser());
    }

    @Override
    @EventHandler
    public void onUserRemovedEvent(UserRemovedEvent event) {
        userRepository.deleteById(event.getId());
    }

}
