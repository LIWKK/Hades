package me.apex.hades.event.impl.packetevents;

import me.apex.hades.event.Event;
import org.bukkit.entity.Entity;

public class VelocityEvent extends Event {

    private final int entityId;
    private final Entity entity;
    private final double velX;
    private final double velY;
    private final double velZ;

    public VelocityEvent(int entityId, Entity entity, double velX, double velY, double velZ) {
        this.entityId = entityId;
        this.entity = entity;
        this.velX = velX;
        this.velY = velY;
        this.velZ = velZ;
    }

    public int getEntityId() {
        return entityId;
    }

    public Entity getEntity() {
        return entity;
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
