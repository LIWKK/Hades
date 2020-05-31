package me.purplex.packetevents.utils.entityfinder;

import org.bukkit.World;
import org.bukkit.craftbukkit.v1_14_R1.CraftWorld;
import org.bukkit.entity.Entity;

import net.minecraft.server.v1_14_R1.WorldServer;

class EntityFinder_1_14 {
    public static Entity getEntityById(final World world, final int id) {
        WorldServer worldServer = ((CraftWorld) world).getHandle();
        return worldServer.getEntity(id).getBukkitEntity();
    }

}