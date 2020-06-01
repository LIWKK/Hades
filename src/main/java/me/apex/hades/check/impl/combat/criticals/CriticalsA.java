package me.apex.hades.check.impl.combat.criticals;

import me.apex.hades.check.api.Check;
import me.apex.hades.check.api.CheckInfo;
import me.apex.hades.objects.User;
import me.purplex.packetevents.enums.EntityUseAction;
import me.purplex.packetevents.event.impl.PacketReceiveEvent;
import me.purplex.packetevents.packet.Packet;
import me.purplex.packetevents.packetwrappers.in.useentity.WrappedPacketInUseEntity;
import org.bukkit.block.BlockFace;

@CheckInfo(name = "Criticals", type = "A")
public class CriticalsA extends Check {
    @Override
    public void onPacket(PacketReceiveEvent e, User user) {
        if (e.getPacketName().equalsIgnoreCase(Packet.Client.USE_ENTITY)){
            WrappedPacketInUseEntity packet = new WrappedPacketInUseEntity(e.getPlayer(), e.getPacket());
            if (packet.getAction() == EntityUseAction.ATTACK){
                if (user.getLocation().getBlock().getRelative(BlockFace.DOWN).isLiquid() || user.getLocation().getBlock().getRelative(BlockFace.UP).isLiquid()) return;

                if (!user.getPlayer().isOnGround() && !user.getPlayer().isFlying()) {
                    if (user.getLocation().getY() % 1.0D == 0.0D) {
                        if (user.getLocation().getBlock().getRelative(BlockFace.DOWN).getType().isSolid()) {
                            flag(user, "hit criticals whilst on ground.");
                        }
                    }
                }
            }
        }
    }
}