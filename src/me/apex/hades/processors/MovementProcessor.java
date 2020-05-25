package me.apex.hades.processors;

import cc.funkemunky.api.events.impl.PacketReceiveEvent;
import cc.funkemunky.api.tinyprotocol.api.Packet;
import cc.funkemunky.api.tinyprotocol.packet.in.WrappedInBlockDigPacket;
import cc.funkemunky.api.tinyprotocol.packet.in.WrappedInFlyingPacket;
import me.apex.hades.Hades;
import me.apex.hades.objects.User;
import me.apex.hades.utils.PacketUtils;
import me.apex.hades.utils.TaskUtils;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;

public enum MovementProcessor {
    INSTANCE;

    public void processMovement(PacketReceiveEvent e, User user) {
        if (PacketUtils.isFlyingPacket(e.getType())) {
            WrappedInFlyingPacket packet = new WrappedInFlyingPacket(e.getPacket(), e.getPlayer());
            Location location = new Location(user.getPlayer().getWorld(), packet.getX(), packet.getY(), packet.getZ(), packet.getYaw(), packet.getPitch());

            if (user.getLastLocation() != null) {
                if (!e.getType().contains("Look")) {
                    location.setYaw(user.getLocation().getYaw());
                    location.setPitch(user.getLocation().getPitch());
                }

                if (!e.getType().contains("Position")) {
                    location.setX(user.getLocation().getX());
                    location.setY(user.getLocation().getY());
                    location.setZ(user.getLocation().getZ());
                }
            }

            Location lastLocation = user.getLocation() != null ? user.getLocation() : location;

            user.setLastLocation(lastLocation);
            user.setLocation(location);

            double lastDeltaY = user.getDeltaY();
            double deltaY = location.getY() - lastLocation.getY();

            user.setLastDeltaY(lastDeltaY);
            user.setDeltaY(deltaY);

            double lastDeltaXZ = user.getDeltaXZ();
            double deltaXZ = location.toVector().clone().setY(0.0D).distance(lastLocation.toVector().clone().setY(0.0D));

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

            //Check Surroundings
            if (Hades.getInstance().isEnabled()) {
                TaskUtils.run(() -> {
                    if (user.getPlayer() == null || user == null) return;
                    if (user.getPlayer().getLocation().getBlock().getRelative(BlockFace.DOWN).getType().toString().contains("ICE")
                            || user.getPlayer().getLocation().clone().add(0, -0.5, 0).getBlock().getRelative(BlockFace.DOWN).getType().toString().contains("ICE")) {
                        user.setLastOnIce(e.getTimeStamp());
                    }
                    if (user.getPlayer().getLocation().getBlock().getRelative(BlockFace.DOWN).getType().toString().contains("SLIME")) {
                        user.setLastOnSlime(e.getTimeStamp());
                    }
                });
            }

            //Ground Check
            if (!user.onGround())
                user.setAirTicks(user.getAirTicks() + 1);
            else user.setAirTicks(0);

            //Process Optifine
            OptifineProcessor.INSTANCE.processOptifine(user);
        } else if (e.getType().equalsIgnoreCase(Packet.Client.BLOCK_DIG)) {
            WrappedInBlockDigPacket packet = new WrappedInBlockDigPacket(e.getPacket(), e.getPlayer());
            if (packet.getAction() == packet.getAction().START_DESTROY_BLOCK) {
                user.setDigging(true);
            } else if (packet.getAction() == packet.getAction().STOP_DESTROY_BLOCK
                    || packet.getAction() == packet.getAction().ABORT_DESTROY_BLOCK) {
                user.setDigging(false);
            }
        }
    }
}
