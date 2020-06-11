package me.apex.hades.check.impl.movement.scaffold;

import io.github.retrooper.packetevents.event.impl.PacketReceiveEvent;
import io.github.retrooper.packetevents.packet.Packet;
import io.github.retrooper.packetevents.packetwrappers.in.blockplace.WrappedPacketInBlockPlace;
import me.apex.hades.check.api.Check;
import me.apex.hades.check.api.CheckInfo;
import me.apex.hades.objects.User;
import me.apex.hades.utils.PlayerUtils;
import me.apex.hades.utils.TaskUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

@CheckInfo(name = "Scaffold", type = "A")
public class ScaffoldA extends Check {
    int preVL = 0;

    @Override
    public void onPacket(PacketReceiveEvent e, User user) {
        if (e.getPacketName().equalsIgnoreCase(Packet.Client.BLOCK_PLACE) && user.getPlayer().getItemInHand().getType().isBlock()) {
            WrappedPacketInBlockPlace packet = new WrappedPacketInBlockPlace(user.getPlayer(), e.getPacket());
            //This if should fix this
            // The error (#10) at https://github.com/undersquire/Hades/issues/10 should be resolved here
            TaskUtils.run(() -> {
                Block block = new Location(user.getLocation().getWorld(), packet.getBlockPosition().x, packet.getBlockPosition().y, packet.getBlockPosition().y).getBlock();
                if (user.isSneaking()
                        || !PlayerUtils.isOnGround(user.getPlayer())
                        || user.getDeltaXZ() < .2) return;

                if (e.getPlayer().getLocation().subtract(0, 1, 0).getBlock().getType().isSolid()
                        && !e.getPlayer().getLocation().subtract(0, 2, 0).getBlock().getType().isSolid() && e.getPlayer().getLocation().getBlockY() > packet.getBlockPosition().y) {
                    if (preVL++ >= 2){
                        flag(user, "using scaffold idk what to say lol.");
                        if (shouldMitigate()){
                            lagBack(user);
                            block.setType(Material.AIR);
                        }
                    }
                }
            });
        }
    }
}
