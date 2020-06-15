package me.apex.hades.event.impl.packetevents;

import io.github.retrooper.packetevents.enums.EntityUseAction;
import me.apex.hades.event.Event;
import org.bukkit.entity.Entity;

public class AttackEvent extends Event {

    private final int entityId;
    private final Entity entity;
    private final EntityUseAction action;

    public AttackEvent(int entityId, Entity entity, EntityUseAction action) {
        this.entityId = entityId;
        this.entity = entity;
        this.action = action;
    }

    public int getEntityId() {
        return entityId;
    }

    public Entity getEntity() {
        return entity;
    }

    public EntityUseAction getAction() {
        return action;
    }

}
