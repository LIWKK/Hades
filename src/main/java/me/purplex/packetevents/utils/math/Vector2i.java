package me.purplex.packetevents.utils.math;

public class Vector2i {
    public int x, y;

    public Vector2i(final int x, final int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "X: " + x + ", Y: " + y;
    }
}
