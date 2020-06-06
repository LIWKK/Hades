package me.apex.hades.check.impl.movement.scaffold;

import io.github.retrooper.packetevents.event.impl.PacketReceiveEvent;
import io.github.retrooper.packetevents.packet.Packet;
import io.github.retrooper.packetevents.packetwrappers.in.blockplace.WrappedPacketInBlockPlace;
import me.apex.hades.check.api.Check;
import me.apex.hades.check.api.CheckInfo;
import me.apex.hades.objects.User;
import me.apex.hades.utils.PacketUtils;
import me.apex.hades.utils.TaskUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

@CheckInfo(name = "Scaffold", type = "B")
public class ScaffoldB extends Check {

    private long lastFlying;

    @Override
    public void onPacket(PacketReceiveEvent e, User user) {
        if (e.getPacketName().equalsIgnoreCase(Packet.Client.BLOCK_PLACE)) {
            WrappedPacketInBlockPlace packet = new WrappedPacketInBlockPlace(user.getPlayer(), e.getPacket());
            long timeDiff = Math.abs(e.getTimestamp() - lastFlying);

            if (timeDiff < 5) {
                if (vl++ > 10) {
                    flag(user, "diff = " + timeDiff);
                    if (shouldMitigate()){
                        lagBack(user);
                        TaskUtils.run(() -> {
                            Block block = new Location(user.getLocation().getWorld(), packet.getBlockPosition().x, packet.getBlockPosition().y, packet.getBlockPosition().y).getBlock();
                            block.setType(Material.AIR);
                        });
                    }
                }
            } else vl = 0;
        } else if (PacketUtils.isFlyingPacket(e.getPacketName())) {
            lastFlying = e.getTimestamp();
        }
    }

}
