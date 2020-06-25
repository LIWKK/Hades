package me.apex.hades.util.boundingbox;

import me.apex.hades.user.User;
import org.bukkit.Location;
import org.bukkit.util.Vector;

// Credits to Tecnio
// Just a ray class I made with useful methods I needed.

public class Ray {

    private final Vector origin;
    private final Vector direction;

    // Create a ray at the origin pointing in a direction.
    public Ray(Vector origin, Vector direction) {
        this.origin = origin;
        this.direction = direction;
    }

    // Create a ray based on where the player is looking.
    // Origin: Player Eye Location
    // Direction: Player-looking direction
    public static Ray from(User user) {
        return new Ray(user.getPlayer().getEyeLocation().toVector(), user.getLocation().getDirection());
    }

    // (Used for rotating vectors) Creates a vector in the horizontal plane (y=0) perpendicular to a vector.
    public static Vector right(Vector vector) {
        Vector n = vector.clone();
        n = n.setY(0).normalize();
        double x = n.getX();
        n.setX(n.getZ());
        n.setZ(-x);
        return n;
    }

    // Returns a normalized version of this Ray with the Y component set to 0
    public Ray level() {
        return new Ray(origin, direction.setY(0).normalize());
    }

    public Vector getOrigin() {
        return origin;
    }

    public Vector getDirection() {
        return direction;
    }

    public double origin(int i) {
        switch (i) {
            case 0:
                return origin.getX();
            case 1:
                return origin.getY();
            case 2:
                return origin.getZ();
            default:
                return 0;
        }
    }

    public double direction(int i) {
        switch (i) {
            case 0:
                return direction.getX();
            case 1:
                return direction.getY();
            case 2:
                return direction.getZ();
            default:
                return 0;
        }
    }

    // Get a point x distance away from this ray.
    // Can be used to get a point 2 blocks in front of a player's face.
    public Vector getPoint(double distance) {
        return direction.clone().normalize().multiply(distance).add(origin);
    }

    // Same as above, but no need to construct object.
    public static Location getPoint(User player, double distance) {
        Vector point = Ray.from(player).getPoint(distance);
        return new Location(player.getLocation().getWorld(), point.getX(), point.getY(), point.getZ());
    }
}
