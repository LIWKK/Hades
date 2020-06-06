package me.apex.hades.check.impl.combat.criticals;

import io.github.retrooper.packetevents.enums.EntityUseAction;
import io.github.retrooper.packetevents.event.impl.PacketReceiveEvent;
import io.github.retrooper.packetevents.packet.Packet;
import io.github.retrooper.packetevents.packetwrappers.in.useentity.WrappedPacketInUseEntity;
import me.apex.hades.check.api.Check;
import me.apex.hades.check.api.CheckInfo;
import me.apex.hades.objects.User;
import org.bukkit.block.BlockFace;

@CheckInfo(name = "Criticals", type = "A")
public class CriticalsA extends Check {
    int preVL = 0;

    @Override
    public void onPacket(PacketReceiveEvent e, User user) {
        if (e.getPacketName().equalsIgnoreCase(Packet.Client.USE_ENTITY)){
        	WrappedPacketInUseEntity packet = new WrappedPacketInUseEntity(e.getPacket());
            if (packet.getAction() == EntityUseAction.ATTACK){
                if (user.getLocation().getBlock().getRelative(BlockFace.DOWN).isLiquid()
                        || user.getLocation().getBlock().getRelative(BlockFace.UP).isLiquid())
                    return;

                if (!user.isOnGroundVanilla() && !user.isFlying()) {
                    if (user.getLocation().getY() % 1.0D == 0.0D) {
                        if (user.getLocation().getBlock().getRelative(BlockFace.DOWN).getType().isSolid()) {
                            if (preVL++ >= 2){
                                flag(user, "tried to hit critical on ground!");
                                if (shouldMitigate()) e.setCancelled(true);
                            }
                        }
                    }else preVL = 0;
                }
            }
        }
    }
}