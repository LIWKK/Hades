package me.apex.hades.event.impl.packetevents;

import lombok.Getter;
import me.apex.hades.event.Event;

@Getter
public class VelocityEvent extends Event {

    private final int entityId;
    private final double velX;
    private final double velY;
    private final double velZ;

    public VelocityEvent(int entityId, double velX, double velY, double velZ) {
        this.entityId = entityId;
        this.velX = velX;
        this.velY = velY;
        this.velZ = velZ;
    }

}
