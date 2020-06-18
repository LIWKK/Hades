package me.apex.hades.event.impl.packetevents;

import me.apex.hades.event.AnticheatEvent;

public class VelocityEvent extends AnticheatEvent {

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

    public int getEntityId() {
        return entityId;
    }

    public double getVelocityX() {
        return velX;
    }

    public double getVelocityY() {
        return velY;
    }

    public double getVelocityZ() {
        return velZ;
    }

}
