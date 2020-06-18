package me.apex.hades.listeners;

import me.apex.hades.HadesPlugin;
import me.apex.hades.event.AnticheatEvent;
import me.apex.hades.event.AnticheatListener;
import me.apex.hades.event.Listen;
import me.apex.hades.event.impl.PacketEvent;
import me.apex.hades.event.impl.packetevents.*;
import me.apex.hades.tinyprotocol.api.Packet;
import me.apex.hades.tinyprotocol.packet.in.*;
import me.apex.hades.tinyprotocol.packet.out.WrappedOutPositionPacket;
import me.apex.hades.tinyprotocol.packet.out.WrappedOutVelocityPacket;
import me.apex.hades.user.User;
import org.bukkit.Bukkit;

public class PacketListener implements AnticheatListener {

    @Listen
    public void onPacket(PacketEvent e) {
        User user = HadesPlugin.userManager.getUser(e.getPlayer().getUniqueId());
        if (user != null) {

            user.getMovementProcessor().update(e.getPacket(), e.getType());

            user.getLagProcessor().update(e.getPacket(), e.getType());

            user.getCombatProcessor().update(e.getPacket(), e.getType());

            //Wrap Packet in Event
            AnticheatEvent checkEvent = e;
            if(e.getType().equalsIgnoreCase(Packet.Client.FLYING) || e.getType().equalsIgnoreCase(Packet.Client.LOOK)
                    || e.getType().equalsIgnoreCase(Packet.Client.POSITION)
                    || e.getType().equalsIgnoreCase(Packet.Client.POSITION_LOOK)) {
                WrappedInFlyingPacket packet = new WrappedInFlyingPacket(e.getPacket(), user.getPlayer());
                checkEvent = new FlyingPacketEvent(packet.getX(), packet.getY(), packet.getZ(), packet.getYaw(),
                        packet.getPitch(),
                        packet.isGround(),
                        packet.isPos(),
                        packet.isLook());
            }else if(e.getType().equalsIgnoreCase(Packet.Client.ARM_ANIMATION)) {
                checkEvent = new SwingEvent();
            }else if(e.getType().equalsIgnoreCase(Packet.Client.USE_ENTITY)) {
                WrappedInUseEntityPacket packet = new WrappedInUseEntityPacket(e.getPacket(), e.getPlayer());
                if(packet.getAction() == WrappedInUseEntityPacket.EnumEntityUseAction.ATTACK) {
                    checkEvent = new AttackEvent(packet.getId(), packet.getEntity());
                }else if(packet.getAction() == WrappedInUseEntityPacket.EnumEntityUseAction.INTERACT
                        || packet.getAction() == WrappedInUseEntityPacket.EnumEntityUseAction.INTERACT_AT) {
                    checkEvent = new EntityInteractEvent(packet.getId(), packet.getEntity());
                }else checkEvent = e;
            }else if(e.getType().equalsIgnoreCase(Packet.Client.CHAT)) {
                WrappedInChatPacket packet = new WrappedInChatPacket(e.getPacket(), e.getPlayer());
                checkEvent = new ChatEvent(packet.getMessage());
            }else if(e.getType().equalsIgnoreCase(Packet.Client.BLOCK_DIG)) {
                WrappedInBlockDigPacket packet = new WrappedInBlockDigPacket(e.getPacket(), e.getPlayer());
                checkEvent = new DigEvent(packet.getPosition(), packet.getDirection(), packet.getAction());
            }else if(e.getType().equalsIgnoreCase(Packet.Client.ENTITY_ACTION)) {
                WrappedInEntityActionPacket packet = new WrappedInEntityActionPacket(e.getPacket(), e.getPlayer());
                checkEvent = new EntityActionEvent(packet.getAction());
            }else if(e.getType().equalsIgnoreCase(Packet.Client.KEEP_ALIVE)) {
                checkEvent = new PingEvent();
            }else if(e.getType().equalsIgnoreCase(Packet.Client.BLOCK_PLACE)) {
                WrappedInBlockPlacePacket packet = new WrappedInBlockPlacePacket(e.getPacket(), e.getPlayer());
                checkEvent = new PlaceEvent(packet.getPosition(), packet.getItemStack());
            }else if(e.getType().equalsIgnoreCase(Packet.Server.POSITION)) {
                WrappedOutPositionPacket packet = new WrappedOutPositionPacket(e.getPacket(), e.getPlayer());
                checkEvent = new TeleportEvent(packet.getX(), packet.getY(), packet.getZ(), packet.getYaw(), packet.getPitch());
            }else if(e.getType().equalsIgnoreCase(Packet.Server.ENTITY_VELOCITY)) {
                WrappedOutVelocityPacket packet = new WrappedOutVelocityPacket(e.getPacket(), e.getPlayer());
                if(packet.getId() == e.getPlayer().getEntityId()) {
                    checkEvent = new VelocityEvent(packet.getId(), packet.getX(), packet.getY(), packet.getZ());
                }
            }

            AnticheatEvent finalCheckEvent = checkEvent;
            user.getCheckList().forEach(check -> check.onHandle(user, finalCheckEvent));
        }
    }
}
