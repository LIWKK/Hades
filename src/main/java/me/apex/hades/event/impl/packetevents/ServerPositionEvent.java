package me.apex.hades.event.impl.packetevents;

import me.apex.hades.event.Event;

public class ServerPositionEvent extends Event {

    private final double x;
    private final double y;
    private final double z;

    public ServerPositionEvent(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
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

}
