package me.apex.hades.utils;

import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.util.NumberConversions;
import org.bukkit.util.Vector;

public class Location {

    public double x, y, z;
    public float yaw, pitch;

    public Location(double x, double y, double z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Location(double x, double y, double z, float yaw, float pitch)
    {
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
    }

    public Vector toVector() { return new Vector(this.x, this.y, this.z); }

    public int getBlockX() { return locToBlock(this.x); }
    public int getBlockY() { return locToBlock(this.y); }
    public int getBlockZ() { return locToBlock(this.z); }

    public Block getBlock(World world) { return BlockUtils.getBlockAsync(new org.bukkit.Location(world, this.getBlockX(), this.getBlockY(), this.getBlockZ())); }

    public Location clone()
    {
        return new Location(this.x, this.y, this.z);
    }

    public Location add(double x, double y, double z)
    {
        this.x += x;
        this.y += y;
        this.z += z;

        return this;
    }

    public static int locToBlock(double choord) { return NumberConversions.floor(choord); }

    public static Location fromBukkit(org.bukkit.Location location)
    {
        return new Location(location.getX(), location.getY(), location.getZ());
    }

    public static org.bukkit.Location toBukkit(Location location, World world)
    {
        return new org.bukkit.Location(world, location.x, location.y, location.z);
    }

}
