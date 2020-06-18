package me.apex.hades.event.impl.bukkitevents;

import me.apex.hades.event.AnticheatEvent;
import org.bukkit.Location;

public class MoveEvent extends AnticheatEvent {

    private final Location to;
    private final Location from;

    public MoveEvent(Location to, Location from) {
        this.to = to;
        this.from = from;
    }

    public Location getTo() {
        return to;
    }

    public Location getFrom() {
        return from;
    }

}
