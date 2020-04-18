package me.apex.hades.event.impl;

import me.apex.hades.event.Event;
import org.bukkit.event.player.PlayerTeleportEvent;

public class TeleportEvent extends Event {

    private PlayerTeleportEvent.TeleportCause cause;

    public TeleportEvent(PlayerTeleportEvent.TeleportCause cause)
    {
        this.cause = cause;
    }

    public PlayerTeleportEvent.TeleportCause getCause() { return cause; }

}
