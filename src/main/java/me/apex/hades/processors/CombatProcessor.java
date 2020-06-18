package me.apex.hades.processors;

import lombok.Setter;
import me.apex.hades.HadesPlugin;
import me.apex.hades.tinyprotocol.api.Packet;
import me.apex.hades.tinyprotocol.packet.in.WrappedInUseEntityPacket;
import me.apex.hades.tinyprotocol.packet.out.WrappedOutVelocityPacket;
import me.apex.hades.user.User;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;

@Setter
public class CombatProcessor {
    private User user;

    public void update(Object packet, String type) {
        if (user != null) {

            if (type.equalsIgnoreCase(Packet.Server.ENTITY_VELOCITY)) {
                WrappedOutVelocityPacket wrappedOutVelocityPacket = new WrappedOutVelocityPacket(packet, user.getPlayer());

                if (wrappedOutVelocityPacket.getId() == user.getPlayer().getEntityId()) {
                    user.setLastVelocity(System.currentTimeMillis());

                    double velocityX = wrappedOutVelocityPacket.getX();
                    double velocityY = wrappedOutVelocityPacket.getY();
                    double velocityZ = wrappedOutVelocityPacket.getZ();

                    double horizontal = Math.hypot(velocityX, velocityZ);
                    double vertical = Math.pow(velocityY + 2.0, 2.0) * 5.0;

                    user.setHorizontalVelocity(horizontal);
                    user.setVerticalVelocity(vertical);

                    if (user.isOnGround() && user.getPlayer().getLocation().getY() % 1.0 == 0.0) {
                        user.setVerticalVelocity(velocityY);
                    }
                }
            }
            if (type.equalsIgnoreCase(Packet.Client.USE_ENTITY)) {

                WrappedInUseEntityPacket wrappedInUseEntityPacket = new WrappedInUseEntityPacket(packet, user.getPlayer());

                if (wrappedInUseEntityPacket.getAction() == WrappedInUseEntityPacket.EnumEntityUseAction.ATTACK && wrappedInUseEntityPacket.getEntity() != null) {

                    if (wrappedInUseEntityPacket.getEntity() instanceof Player) {
                        User attackedUser = HadesPlugin.userManager.getUser(wrappedInUseEntityPacket.getEntity().getUniqueId());
                        if (attackedUser != null) user.setTargetUser(attackedUser);
                    }

                    if (user.getLastEntityAttacked() != null) {
                        if (user.getLastEntityAttacked() != wrappedInUseEntityPacket.getEntity()) {
                            user.constantEntityTicks = 0;
                        } else {
                            user.constantEntityTicks++;
                        }
                    }

                    if (wrappedInUseEntityPacket.getEntity() instanceof Player || wrappedInUseEntityPacket.getEntity() instanceof Villager) {
                        user.setLastEntityAttacked(wrappedInUseEntityPacket.getEntity());
                        user.setLastUseEntityPacket(System.currentTimeMillis());
                    }
                }
            }
        }
    }
}
