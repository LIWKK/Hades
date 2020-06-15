package me.apex.hades.processor.impl;

import io.github.retrooper.packetevents.event.impl.PacketReceiveEvent;
import io.github.retrooper.packetevents.packetwrappers.in.flying.WrappedPacketInFlying;
import me.apex.hades.processor.Processor;
import me.apex.hades.user.User;
import me.apex.hades.util.PacketUtil;
import me.apex.hades.util.TaskUtil;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;

public class MovementProcessor extends Processor {

    public MovementProcessor(User user) {
        super(user);
    }

    @Override
    public void process(PacketReceiveEvent e) {
        if (PacketUtil.isPositionPacket(e.getPacketName())) {
            WrappedPacketInFlying packet = new WrappedPacketInFlying(e.getPacket());
            Location location = new Location(user.player.getWorld(), packet.getX(), packet.getY(), packet.getZ(), packet.getYaw(), packet.getPitch());
            Location lastLocation = user.location != null ? user.location : location;

            user.lastLocation = lastLocation;
            user.location = location;

            double lastDeltaY = user.deltaY;
            double deltaY = location.getY() - lastLocation.getY();

            user.lastDeltaY = lastDeltaY;
            user.deltaY = deltaY;

            double lastDeltaXZ = user.deltaXZ;
            double deltaXZ = location.clone().toVector().setY(0.0).distance(lastLocation.clone().toVector().setY(0.0));

            user.lastDeltaXZ = lastDeltaXZ;
            user.deltaXZ = deltaXZ;

            float lastDeltaYaw = user.deltaYaw;
            float deltaYaw = Math.abs(location.getYaw() - lastLocation.getYaw());

            user.lastDeltaYaw = lastDeltaYaw;
            user.deltaYaw = deltaYaw;

            float lastDeltaPitch = user.deltaPitch;
            float deltaPitch = Math.abs(location.getPitch() - lastLocation.getPitch());

            user.lastDeltaPitch = lastDeltaPitch;
            user.deltaPitch = deltaPitch;

            TaskUtil.run(() -> {
                if (user.player.getLocation().getBlock().getRelative(BlockFace.DOWN).getType().toString().contains("ICE")
                        || user.player.getLocation().clone().add(0, -0.5, 0).getBlock().getRelative(BlockFace.DOWN).getType().toString().contains("ICE")) {
                    user.iceTick = user.tick;
                }
                if (user.player.getLocation().getBlock().getRelative(BlockFace.DOWN).getType().toString().contains("SLIME")) {
                    user.slimeTick = user.tick;
                }
                if (user.player.getEyeLocation().getBlock().getType().isSolid()
                        || user.player.getEyeLocation().getBlock().getRelative(BlockFace.UP).getType().isSolid()) {
                    user.underBlockTick = user.tick;
                }
            });
        } else if (PacketUtil.isRotationPacket(e.getPacketName())) {
            WrappedPacketInFlying packet = new WrappedPacketInFlying(e.getPacket());
            Location location = new Location(user.player.getWorld(), user.location.getX(), user.location.getY(), user.location.getZ(), packet.getYaw(), packet.getPitch());
            Location lastLocation = user.location != null ? user.location : location;

            float lastDeltaYaw = user.deltaYaw;
            float deltaYaw = Math.abs(location.getYaw() - lastLocation.getYaw());

            user.lastDeltaYaw = lastDeltaYaw;
            user.deltaYaw = deltaYaw;

            float lastDeltaPitch = user.deltaPitch;
            float deltaPitch = Math.abs(location.getPitch() - lastLocation.getPitch());

            user.lastDeltaPitch = lastDeltaPitch;
            user.deltaPitch = deltaPitch;
        }
    }

}
