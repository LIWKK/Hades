package me.apex.hades.check.impl.movement.scaffold;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

import me.apex.hades.check.api.Check;
import me.apex.hades.check.api.CheckInfo;
import me.apex.hades.objects.User;
import me.apex.hades.utils.PacketUtils;
import me.purplex.packetevents.event.impl.PacketReceiveEvent;
import me.purplex.packetevents.packet.Packet;
import me.purplex.packetevents.packetwrappers.in.blockplace.WrappedPacketInBlockPlace;

@CheckInfo(name = "Scaffold", type = "A")
public class ScaffoldA extends Check {

    @Override
    public void onPacket(PacketReceiveEvent e, User user) {
        if (e.getPacketName().equalsIgnoreCase(Packet.Client.BLOCK_PLACE)) {
        	WrappedPacketInBlockPlace packet = new WrappedPacketInBlockPlace(e.getPlayer(), e.getPacket());

            if (PacketUtils.isBlockPacket(packet.getItemStack().getType().toString()) || !user.getPlayer().getItemInHand().getType().isBlock() || user.getPlayer().getItemInHand().getType() == Material.AIR)
                return;

            Block placed = new Location(user.getPlayer().getWorld(), packet.getBlockPosition().x, packet.getBlockPosition().y, packet.getBlockPosition().z).getBlock();
            if (placed == null) return;

            Location from = user.getLastLocation();
            int x = (int) (placed.getX() - from.getX());
            int y = (int) (placed.getY() - from.getY());
            int z = (int) (placed.getZ() - from.getZ());
            double a = Math.abs(from.getBlockX() - placed.getX());
            double b = Math.abs(from.getBlockZ() - placed.getZ());

            if (from.clone().add(0, -2, 0).getBlock().getType() == Material.AIR && (x == 0 || z == 0) && y == -1 && (a == 0 && b == 0) && !user.getPlayer().isSneaking())
                flag(user, "offset = " + a + ", " + b);
        }
    }

}
