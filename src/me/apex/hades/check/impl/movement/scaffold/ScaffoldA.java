package me.apex.hades.check.impl.movement.scaffold;

import cc.funkemunky.api.events.impl.PacketReceiveEvent;
import cc.funkemunky.api.tinyprotocol.api.Packet;
import cc.funkemunky.api.tinyprotocol.packet.in.WrappedInBlockPlacePacket;
import me.apex.hades.check.api.Check;
import me.apex.hades.check.api.CheckInfo;
import me.apex.hades.data.User;
import me.apex.hades.utils.PacketUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

@CheckInfo(Name = "Scaffold (A)", Type = Check.CheckType.MOVEMENT, Experimental = false)
public class ScaffoldA extends Check {

    @Override
    public void onPacket(PacketReceiveEvent e, User user) {
        if(e.getType().equalsIgnoreCase(Packet.Client.BLOCK_PLACE))
        {
            WrappedInBlockPlacePacket packet = new WrappedInBlockPlacePacket(e.getPacket(), e.getPlayer());

            if(PacketUtils.isBlockPacket(packet.getItemStack().getType().toString()) || !user.getPlayer().getItemInHand().getType().isBlock() || user.getPlayer().getItemInHand().getType() == Material.AIR)
                return;

            Block placed = new Location(user.getPlayer().getWorld(), packet.getPosition().getX(), packet.getPosition().getY(), packet.getPosition().getZ()).getBlock();
            if(placed == null) return;

            Location from = user.getLastLocation();
            int x = (int) (placed.getX() - from.getX());
            int y = (int) (placed.getY() - from.getY());
            int z = (int) (placed.getZ() - from.getZ());
            double a = Math.abs(from.getBlockX() - placed.getX());
            double b = Math.abs(from.getBlockZ() - placed.getZ());

            if(from.clone().add(0, -2, 0).getBlock().getType() == Material.AIR && (x == 0 || z == 0) && y == -1 && (a == 0 && b == 0) && !user.isLagging())
                flag(user, "offset = " + a + ", " + b);
        }
    }

}
