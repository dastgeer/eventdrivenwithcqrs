package com.springbank.user.cmd.api.aggregates;

import com.springbank.user.cmd.api.commands.RegisterUserCommand;
import com.springbank.user.cmd.api.commands.RemoveUserCommand;
import com.springbank.user.cmd.api.commands.UpdateUserCommand;
import com.springbank.user.cmd.api.security.PasswordEncoder;
import com.springbank.user.cmd.api.security.PasswordEncoderImpl;
import com.springbank.user.core.events.UserRegisteredEvent;
import com.springbank.user.core.events.UserRemovedEvent;
import com.springbank.user.core.events.UserUpdatedEvent;
import com.springbank.user.core.models.User;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.factory.annotation.Autowired;

@Aggregate
public class UserAggregates {
    @AggregateIdentifier
    private String id;
    private User user;

    private final PasswordEncoder passwordEncoder;

    public UserAggregates(){
        //this.passwordEncoder = new PasswordEncoderImpl();
        passwordEncoder = new PasswordEncoderImpl();
    }


    @CommandHandler
    public  UserAggregates(RegisterUserCommand registerUserCommand){
        User  newRegisterUser = registerUserCommand.getUser();
        newRegisterUser.setId(registerUserCommand.getId());
        String rawPassword= newRegisterUser.getAccount().getPassword();
        passwordEncoder = new PasswordEncoderImpl();
        String hashedPassword = passwordEncoder.hashPassword(rawPassword);
        newRegisterUser.getAccount().setPassword(hashedPassword);

        UserRegisteredEvent registeredEvent= UserRegisteredEvent.builder().id(registerUserCommand.getId()).user(newRegisterUser).build();
        AggregateLifecycle.apply(registeredEvent);
    }

    @CommandHandler
    public void UpdateUserAggregates(UpdateUserCommand updateUserCommand){
        User updateUser= updateUserCommand.getUser();
        updateUser.setId(updateUserCommand.getId());
        String password = updateUser.getAccount().getPassword();
        String hashedPassword = new PasswordEncoderImpl().hashPassword(password);
        updateUser.getAccount().setPassword(hashedPassword);

        UserUpdatedEvent userUpdatedEvent= UserUpdatedEvent.builder().id(updateUserCommand.getId()).user(updateUser).build();
        AggregateLifecycle.apply(userUpdatedEvent);

    }

    @CommandHandler
    public void RemoveUserAggregates(RemoveUserCommand removeUserCommand){
         UserRemovedEvent event = new UserRemovedEvent();
        event.setId(removeUserCommand.getId());
        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void onUserRegisterEvent(UserRegisteredEvent event){
        this.id = event.getId();
        this.user = event.getUser();
    }

    @EventSourcingHandler
    public void onUserUpdateEvent(UserUpdatedEvent event){
        this.user = event.getUser();
    }

    @EventSourcingHandler
    public void onUserRemovedEvent(UserRemovedEvent event){
        this.id = event.getId();
        this.user.setId(event.getId());
    }

}
