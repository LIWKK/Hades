package me.apex.hades.utils;


import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

// Credits to Tecnio

public class AABB {

    private Vector min, max; // min/max locations

    // Create Bounding Box from min/max locations.
    public AABB(Vector min, Vector max) {
        this(min.getX(), min.getY(), min.getZ(), max.getX(), max.getY(), max.getZ());
    }

    // Main constructor for AABB
    public AABB(double x1, double y1, double z1, double x2, double y2, double z2) {
        this.min = new Vector(Math.min(x1, x2), Math.min(y1, y2), Math.min(z1, z2));
        this.max = new Vector(Math.max(x1, x2), Math.max(y1, y2), Math.max(z1, z2));
    }

    private AABB(Player player) {
        this.min = getMin(player);
        this.max = getMax(player);
    }

    private Vector getMin(Player player) {
        return player.getLocation().toVector().add(new Vector(-0.3, 0, -0.3));
    }

    private Vector getMax(Player player) {
        return player.getLocation().toVector().add(new Vector(0.3, 1.8, 0.3));
    }

    // Create an AABB based on a player's hitbox
    public static AABB from(Player player) {
        return new AABB(player);
    }

    public Vector getMin() {
        return min;
    }

    public Vector getMax() {
        return max;
    }

    // Returns minimum x, y, or z point from inputs 0, 1, or 2.
    public double min(int i) {
        switch (i) {
            case 0:
                return min.getX();
            case 1:
                return min.getY();
            case 2:
                return min.getZ();
            default:
                return 0;
        }
    }

    // Returns maximum x, y, or z point from inputs 0, 1, or 2.
    public double max(int i) {
        switch (i) {
            case 0:
                return max.getX();
            case 1:
                return max.getY();
            case 2:
                return max.getZ();
            default:
                return 0;
        }
    }

    // Check if a Ray passes through this box. tmin and tmax are the bounds.
    // Example: If you wanted to see if the Ray collides anywhere from its
    // origin to 5 units away, the values would be 0 and 5.
    public boolean collides(Ray ray, double tmin, double tmax) {
        for (int i = 0; i < 3; i++) {
            double d = 1 / ray.direction(i);
            double t0 = (min(i) - ray.origin(i)) * d;
            double t1 = (max(i) - ray.origin(i)) * d;
            if (d < 0) {
                double t = t0;
                t0 = t1;
                t1 = t;
            }
            tmin = t0 > tmin ? t0 : tmin;
            tmax = t1 < tmax ? t1 : tmax;
            if (tmax <= tmin) return false;
        }
        return true;
    }

    // Same as other collides method, but returns the distance of the nearest
    // point of collision of the ray and box, or -1 if no collision.
    public double collidesD(Ray ray, double tmin, double tmax) {
        for (int i = 0; i < 3; i++) {
            double d = 1 / ray.direction(i);
            double t0 = (min(i) - ray.origin(i)) * d;
            double t1 = (max(i) - ray.origin(i)) * d;
            if (d < 0) {
                double t = t0;
                t0 = t1;
                t1 = t;
            }
            tmin = t0 > tmin ? t0 : tmin;
            tmax = t1 < tmax ? t1 : tmax;
            if (tmax <= tmin) return -1;
        }
        return tmin;
    }

    // Check if the location is in this box.
    public boolean contains(Location location) {
        if (location.getX() > max.getX()) return false;
        if (location.getY() > max.getY()) return false;
        if (location.getZ() > max.getZ()) return false;
        if (location.getX() < min.getX()) return false;
        if (location.getY() < min.getY()) return false;
        if (location.getZ() < min.getZ()) return false;
        return true;
    }
}