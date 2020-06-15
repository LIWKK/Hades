package me.apex.hades.event.impl.packetevents;

import io.github.retrooper.packetevents.enums.PlayerAction;
import me.apex.hades.event.Event;
import org.bukkit.entity.Entity;

public class EntityActionEvent extends Event {

    private final int entityId;
    private final Entity entity;
    private final int jumpBoost;
    private final PlayerAction action;

    public EntityActionEvent(int entityId, Entity entity, int jumpBoost, PlayerAction action) {
        this.entityId = entityId;
        this.entity = entity;
        this.jumpBoost = jumpBoost;
        this.action = action;
    }

    public int getEntityId() {
        return entityId;
    }

    public Entity getEntity() {
        return entity;
    }

    public int getJumpBoost() {
        return jumpBoost;
    }

    public PlayerAction getAction() {
        return action;
    }

}
