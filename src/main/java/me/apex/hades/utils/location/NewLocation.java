package me.apex.hades.utils.location;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewLocation {
    public double x, minX, maxX;
    private double y, minY, maxY;
    public double z, minZ, maxZ;

    public double hitboxSize = 0.0;

    private float yaw, pitch;

    public NewLocation(double x, double y, double z, float yaw, float pitch) {
        this.x = x;
        this.y = y;
        this.z = z;

        maxX = x + hitboxSize;
        minX = x - hitboxSize;

        maxZ = z + hitboxSize;
        minZ = z - hitboxSize;

        this.yaw = yaw;
        this.pitch = pitch;

    }
}
