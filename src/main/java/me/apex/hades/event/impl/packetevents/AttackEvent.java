package me.apex.hades.event.impl.packetevents;

import me.apex.hades.event.AnticheatEvent;
import org.bukkit.entity.Entity;

public class AttackEvent extends AnticheatEvent {

    private final int entityId;
    private final Entity entity;

    public AttackEvent(int entityId, Entity entity) {
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
