package me.apex.hades.processor;

import io.github.retrooper.packetevents.event.impl.PacketReceiveEvent;
import io.github.retrooper.packetevents.packet.Packet;
import io.github.retrooper.packetevents.packetwrappers.in.flying.WrappedPacketInFlying;
import me.apex.hades.user.User;
import me.apex.hades.util.PacketUtil;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public class MovementProcessor {

    public static void process(User user, PacketReceiveEvent e) {
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

            //Update Flying
            if (user.getPlayer().isFlying()) user.setFlyingTick(user.getTick());

            //Update Block Check
            BlockProcessor.process(user);

            //Update Optifine
            OptifineProcessor.process(user);

            user.setDeltaAngle(Math.abs(user.getDeltaYaw()) + Math.abs(user.getDeltaPitch()));

            user.setDirection(new Vector(-Math.sin(user.getPlayer().getEyeLocation().getYaw() * 3.1415927F / 180.0F) * (float) 1 * 0.5F, 0, Math.cos(user.getPlayer().getEyeLocation().getYaw() * 3.1415927F / 180.0F) * (float) 1 * 0.5F));
        } else if (PacketUtil.isRotationPacket(e.getPacketName())) {
            WrappedPacketInFlying packet = new WrappedPacketInFlying(e.getPacket());
            user.setOnGround(packet.isOnGround());

            Location location = new Location(user.getPlayer().getWorld(), user.getLocation().getX(), user.getLocation().getY(), user.getLocation().getZ(), packet.getYaw(), packet.getPitch());
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

            //Update Flying
            if (user.getPlayer().isFlying()) user.setFlyingTick(user.getTick());

            //Update Block Check
            BlockProcessor.process(user);

            //Update Optifine
            OptifineProcessor.process(user);

            user.setDeltaAngle(Math.abs(user.getDeltaYaw()) + Math.abs(user.getDeltaPitch()));

            user.setDirection(new Vector(-Math.sin(user.getPlayer().getEyeLocation().getYaw() * 3.1415927F / 180.0F) * (float) 1 * 0.5F, 0, Math.cos(user.getPlayer().getEyeLocation().getYaw() * 3.1415927F / 180.0F) * (float) 1 * 0.5F));
        } else if (e.getPacketName().equalsIgnoreCase(Packet.Client.FLYING)) {
            WrappedPacketInFlying packet = new WrappedPacketInFlying(e.getPacket());
            user.setOnGround(packet.isOnGround());

            Location location = new Location(user.getPlayer().getWorld(), user.getLocation().getX(), user.getLocation().getY(), user.getLocation().getZ(), user.getLocation().getYaw(), user.getLocation().getPitch());
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

            //Update Flying
            if (user.getPlayer().isFlying()) user.setFlyingTick(user.getTick());

            //Update Block Check
            BlockProcessor.process(user);

            //Update Optifine
            OptifineProcessor.process(user);

            user.setDeltaAngle(Math.abs(user.getDeltaYaw()) + Math.abs(user.getDeltaPitch()));

            user.setDirection(new Vector(-Math.sin(user.getPlayer().getEyeLocation().getYaw() * 3.1415927F / 180.0F) * (float) 1 * 0.5F, 0, Math.cos(user.getPlayer().getEyeLocation().getYaw() * 3.1415927F / 180.0F) * (float) 1 * 0.5F));
        } else if (PacketUtil.isFlyingPacket(e.getPacketName())) {
            if (user.isSprinting()) {
                user.setSprintingTicks(user.getSprintingTicks() + 1);
            } else if (!user.isSprinting()) {
                user.setSprintingTicks(0);
            }

            if (user.getLocations().size() >= 20){
                user.getLocations().remove(0);
            }
            user.getLocations().add(user.getLocation());
        }
    }

}
