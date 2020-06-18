package me.apex.hades.event.impl.packetevents;

import io.github.retrooper.packetevents.enums.Direction;
import io.github.retrooper.packetevents.enums.PlayerDigType;
import io.github.retrooper.packetevents.utils.vector.Vector3i;
import me.apex.hades.event.Event;

public class DigEvent extends Event {

    private final Vector3i blockPos;
    private final Direction direction;
    private final PlayerDigType digType;

    public DigEvent(Vector3i blockPos, Direction direction, PlayerDigType digType) {
        this.blockPos = blockPos;
        this.direction = direction;
        this.digType = digType;
    }

    public Vector3i getBlockPos() {
        return blockPos;
    }

    public Direction getDirection() {
        return direction;
    }

    public PlayerDigType getDigType() {
        return digType;
    }

}
