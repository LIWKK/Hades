package me.apex.hades.event.impl.packetevents;

import lombok.Getter;
import me.apex.hades.event.Event;

@Getter
public class TeleportEvent extends Event {

    private final double x;
    private final double y;
    private final double z;
    private final float yaw;
    private final float pitch;

    public TeleportEvent(double x, double y, double z, float yaw, float pitch) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
    }

}
