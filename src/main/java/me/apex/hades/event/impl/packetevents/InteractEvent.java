package me.apex.hades.event.impl.packetevents;

import me.apex.hades.event.Event;
import org.bukkit.entity.Entity;

public class InteractEvent extends Event {

    private final int entityId;
    private final Entity entity;

    public InteractEvent(int entityId, Entity entity) {
        this.entityId = entityId;
        this.entity = entity;
    }

    public int getEntityId() {
        return entityId;
    }

    public Entity getEntity() {
        return entity;
    }

}
