package me.apex.hades.event.impl.packetevents;

import me.apex.hades.event.Event;

public class FlyingEvent extends Event {

    private final double x;
    private final double y;
    private final double z;
    private final float yaw;
    private final float pitch;
    private final boolean hasMoved;
    private final boolean hasLooked;
    private final boolean isOnGround;

    public FlyingEvent(double x, double y, double z, float yaw, float pitch, boolean hasMoved, boolean hasLooked, boolean isOnGround) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
        this.hasMoved = hasMoved;
        this.hasLooked = hasLooked;
        this.isOnGround = isOnGround;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public float getYaw() {
        return yaw;
    }

    public float getPitch() {
        return pitch;
    }

    public boolean hasMoved() {
        return hasMoved;
    }

    public boolean hasLooked() {
        return hasLooked;
    }

    public boolean isOnGround() {
        return isOnGround;
    }

}
