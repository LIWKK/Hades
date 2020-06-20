package me.apex.hades.processor.impl;

import io.github.retrooper.packetevents.event.impl.PacketReceiveEvent;
import io.github.retrooper.packetevents.packet.Packet;
import io.github.retrooper.packetevents.packetwrappers.in.flying.WrappedPacketInFlying;
import me.apex.hades.processor.Processor;
import me.apex.hades.user.User;
import me.apex.hades.util.PacketUtil;
import org.bukkit.Location;

public class MovementProcessor extends Processor {

    public MovementProcessor(User user) {
        super(user);
    }

    public void process(PacketReceiveEvent e) {
        if (PacketUtil.isPositionPacket(e.getPacketName())) {
            WrappedPacketInFlying packet = new WrappedPacketInFlying(e.getPacket());
            user.setOnGround(packet.isOnGround());

            Location location = new Location(user.getPlayer().getWorld(), packet.getX(), packet.getY(), packet.getZ(), packet.getYaw(), packet.getPitch());
            Location lastLocation = user.getLocation() != null ? user.getLocation() : location;

            user.setLastLocation(lastLocation);
            user.setLocation(location);

            double lastDeltaY = user.getDeltaY();
            double deltaY = location.getY() - lastLocation.getY();

            user.setLastDeltaY(lastDeltaY);
            user.setDeltaY(deltaY);

            double lastDeltaXZ = user.getDeltaXZ();
            double deltaXZ = location.clone().toVector().setY(0.0).distance(lastLocation.clone().toVector().setY(0.0));

            user.setLastDeltaXZ(lastDeltaXZ);
            user.setDeltaXZ(deltaXZ);

            float lastDeltaYaw = user.getDeltaYaw();
            float deltaYaw = Math.abs(location.getYaw() - lastLocation.getYaw());

            user.setLastDeltaYaw(lastDeltaYaw);
            user.setDeltaYaw(deltaYaw);

            float lastDeltaPitch = user.getDeltaPitch();
            float deltaPitch = Math.abs(location.getPitch() - lastLocation.getPitch());

            user.setLastDeltaPitch(lastDeltaPitch);
            user.setDeltaPitch(deltaPitch);

            //Update Block Check
            ((BlockProcessor)user.getBlockProcessor()).process(user);

            //Update Optifine
            ((OptifineProcessor)user.getOptifineProcessor()).process(user);

        } else if (PacketUtil.isRotationPacket(e.getPacketName())) {
            WrappedPacketInFlying packet = new WrappedPacketInFlying(e.getPacket());
            user.setOnGround(packet.isOnGround());

            Location location = new Location(user.getPlayer().getWorld(), user.getLocation().getX(), user.getLocation().getY(), user.getLocation().getZ(), packet.getYaw(), packet.getPitch());
            Location lastLocation = user.getLocation() != null ? user.getLocation() : location;

            float lastDeltaYaw = user.getDeltaYaw();
            float deltaYaw = Math.abs(location.getYaw() - lastLocation.getYaw());

            user.setLastDeltaYaw(user.getDeltaYaw());
            user.setDeltaYaw(deltaYaw);

            float lastDeltaPitch = user.getDeltaPitch();
            float deltaPitch = Math.abs(location.getPitch() - lastLocation.getPitch());

            user.setLastDeltaPitch(lastDeltaPitch);
            user.setDeltaPitch(deltaPitch);

            //Update Block Check
            ((BlockProcessor)user.getBlockProcessor()).process(user);

            //Update Optifine
            ((OptifineProcessor)user.getOptifineProcessor()).process(user);
        }else if(e.getPacketName().equalsIgnoreCase(Packet.Client.FLYING)) {
            WrappedPacketInFlying packet = new WrappedPacketInFlying(e.getPacket());
            user.setOnGround(packet.isOnGround());

            //Update Block Check
            ((BlockProcessor)user.getBlockProcessor()).process(user);
        }
    }

}
