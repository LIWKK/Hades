package me.apex.hades.event.impl;

import me.apex.hades.event.Event;
import org.bukkit.event.Cancellable;

public class CommandEvent extends Event implements Cancellable {

    private String message;

    public CommandEvent(String message)
    {
        this.message = message;
    }

    public String getMessage() { return message; }

}
