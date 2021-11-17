package com.springbank.user.query.api.handlers;

import com.springbank.user.core.events.UserRegisteredEvent;
import com.springbank.user.core.events.UserRemovedEvent;
import com.springbank.user.core.events.UserUpdatedEvent;

public interface UserEventHandlers {

    public void onUserRegisterEvent(UserRegisteredEvent event);

    public void onUserUpdatedEvent(UserUpdatedEvent event);

    public void onUserRemovedEvent(UserRemovedEvent event);
}
