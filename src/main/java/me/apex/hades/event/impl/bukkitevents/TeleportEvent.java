package me.apex.hades.event.impl.bukkitevents;

import me.apex.hades.event.Event;
import org.bukkit.Location;
import org.bukkit.event.player.PlayerTeleportEvent;

public class TeleportEvent extends Event {

    private final Location to;
    private final Location from;
    private final PlayerTeleportEvent.TeleportCause cause;

    public TeleportEvent(Location to, Location from, PlayerTeleportEvent.TeleportCause cause) {
        this.to = to;
        this.from = from;
        this.cause = cause;
    }

    public Location getTo() {
        return to;
    }

    public Location getFrom() {
        return from;
    }

    public PlayerTeleportEvent.TeleportCause getCause() {
        return cause;
    }

}
