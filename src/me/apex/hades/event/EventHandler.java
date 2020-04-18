package me.apex.hades.event;

import me.apex.hades.data.User;

public abstract class EventHandler {

    public User user;

    public EventHandler(User user)
    {
        this.user = user;
    }

    public abstract void handle(Event event);

}
