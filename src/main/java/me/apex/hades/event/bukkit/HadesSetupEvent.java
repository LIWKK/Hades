package me.apex.hades.event.bukkit;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class HadesSetupEvent extends Event {
    private static final HandlerList handlers = new HandlerList();

    public HadesSetupEvent() {
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}