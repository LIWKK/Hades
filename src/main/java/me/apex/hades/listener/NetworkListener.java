package me.apex.hades.listener;

import io.github.retrooper.packetevents.enums.EntityUseAction;
import io.github.retrooper.packetevents.enums.PlayerAction;
import io.github.retrooper.packetevents.enums.PlayerDigType;
import io.github.retrooper.packetevents.event.PacketEvent;
import io.github.retrooper.packetevents.event.PacketHandler;
import io.github.retrooper.packetevents.event.PacketListener;
import io.github.retrooper.packetevents.event.impl.PacketReceiveEvent;
import io.github.retrooper.packetevents.event.impl.PacketSendEvent;
import io.github.retrooper.packetevents.packet.Packet;
import io.github.retrooper.packetevents.packetwrappers.in.blockdig.WrappedPacketInBlockDig;
import io.github.retrooper.packetevents.packetwrappers.in.blockplace.WrappedPacketInBlockPlace;
import io.github.retrooper.packetevents.packetwrappers.in.chat.WrappedPacketInChat;
import io.github.retrooper.packetevents.packetwrappers.in.entityaction.WrappedPacketInEntityAction;
import io.github.retrooper.packetevents.packetwrappers.in.flying.WrappedPacketInFlying;
import io.github.retrooper.packetevents.packetwrappers.in.useentity.WrappedPacketInUseEntity;
import io.github.retrooper.packetevents.packetwrappers.out.entityvelocity.WrappedPacketOutEntityVelocity;
import me.apex.hades.event.impl.packetevents.*;
import me.apex.hades.user.User;
import me.apex.hades.user.UserManager;
import me.apex.hades.util.PacketUtil;

public class NetworkListener implements PacketListener {

    @PacketHandler
    public void onPacketReceive(PacketReceiveEvent e) {
        User user = UserManager.getUser(e.getPlayer());
        if (user != null) {
            //Update Tick
            user.tick++;

            //Process Movement
            user.movementProcessor.process(e);

            //Player Vars
            if (e.getPacketName().equalsIgnoreCase(Packet.Client.ENTITY_ACTION)){
                WrappedPacketInEntityAction packet = new WrappedPacketInEntityAction(e.getPacket());
                if (packet.getAction().equals(PlayerAction.START_SPRINTING)){
                    user.isSprinting = true;
                }
                if (packet.getAction().equals(PlayerAction.STOP_SPRINTING)){
                    user.isSprinting = false;
                }
                if (packet.getAction().equals(PlayerAction.START_SNEAKING)){
                    user.isSneaking = true;
                }
                if (packet.getAction().equals(PlayerAction.STOP_SNEAKING)){
                    user.isSneaking = false;
                }
            }

            //Call Checks
            PacketEvent callEvent = null;
            if (e.getPacketName().equalsIgnoreCase(Packet.Client.ARM_ANIMATION)) {
                callEvent = new SwingEvent();
            } else if (e.getPacketName().equalsIgnoreCase(Packet.Client.USE_ENTITY)) {
                WrappedPacketInUseEntity packet = new WrappedPacketInUseEntity(e.getPacket());
                if(packet.getAction() == EntityUseAction.ATTACK) {
                    callEvent = new AttackEvent(packet.getEntityId(), packet.getEntity());
                }else if(packet.getAction() == EntityUseAction.INTERACT
                        || packet.getAction() == EntityUseAction.INTERACT_AT) {
                    callEvent = new InteractEvent(packet.getEntityId(), packet.getEntity());
                }else callEvent = e;
            } else if (e.getPacketName().equalsIgnoreCase(Packet.Client.CHAT)) {
                WrappedPacketInChat packet = new WrappedPacketInChat(e.getPacket());
                callEvent = new ChatEvent(packet.getMessage());
            } else if (e.getPacketName().equalsIgnoreCase(Packet.Client.BLOCK_DIG)) {
                WrappedPacketInBlockDig packet = new WrappedPacketInBlockDig(e.getPacket());
                user.digTick = user.tick;
                if (packet.getDigType() == PlayerDigType.START_DESTROY_BLOCK) user.digging = true;
                else if (packet.getDigType() == PlayerDigType.STOP_DESTROY_BLOCK
                        || packet.getDigType() == PlayerDigType.ABORT_DESTROY_BLOCK) user.digging = false;
                callEvent = new DigEvent(packet.getBlockPosition(), packet.getDirection(), packet.getDigType());
            } else if (e.getPacketName().equalsIgnoreCase(Packet.Client.ENTITY_ACTION)) {
                WrappedPacketInEntityAction packet = new WrappedPacketInEntityAction(e.getPacket());
                callEvent = new EntityActionEvent(packet.getEntityId(), packet.getEntity(), packet.getJumpBoost(), packet.getAction());
            } else if (PacketUtil.isFlyingPacket(e.getPacketName())) {
                WrappedPacketInFlying packet = new WrappedPacketInFlying(e.getPacket());
                callEvent = new FlyingEvent(packet.getX(), packet.getY(), packet.getZ(), packet.getYaw(), packet.getPitch(),
                        e.getPacketName().equalsIgnoreCase(Packet.Client.POSITION) || e.getPacketName().equalsIgnoreCase(Packet.Client.POSITION_LOOK),
                        e.getPacketName().equalsIgnoreCase(Packet.Client.LOOK) || e.getPacketName().equalsIgnoreCase(Packet.Client.POSITION_LOOK),
                        packet.isOnGround());
            } else if (e.getPacketName().equalsIgnoreCase(Packet.Client.KEEP_ALIVE)) {
                callEvent = new PingEvent();
            } else if (e.getPacketName().equalsIgnoreCase(Packet.Client.BLOCK_PLACE)) {
                WrappedPacketInBlockPlace packet = new WrappedPacketInBlockPlace(e.getPlayer(), e.getPacket());
                callEvent = new PlaceEvent(packet.getBlockPosition(), packet.getItemStack());

            } else callEvent = e;
            PacketEvent finalCallEvent = callEvent;
            user.checks.stream().filter(check -> check.enabled).forEach(check -> check.onEvent(finalCallEvent, user));
        }
    }

    @PacketHandler
    public void onPacketSend(PacketSendEvent e) {
        User user = UserManager.getUser(e.getPlayer());
        if (user != null) {
            if (e.getPacketName().equalsIgnoreCase(Packet.Server.POSITION)) {
                user.teleportTick = user.tick;
                user.checks.stream().filter(check -> check.enabled).forEach(check -> check.onEvent(new ServerPositionEvent(-1, -1, -1), user));
            } else if (e.getPacketName().equalsIgnoreCase(Packet.Server.ENTITY_VELOCITY)) {
                WrappedPacketOutEntityVelocity packet = new WrappedPacketOutEntityVelocity(e.getPacket());
                if (e.getPlayer().getEntityId() == packet.getEntityId()) {
                    user.checks.stream().filter(check -> check.enabled).forEach(check -> check.onEvent(new VelocityEvent(packet.getEntityId(), packet.getEntity(), packet.getVelocityX(), packet.getVelocityY(), packet.getVelocityZ()), user));
                }
            } else {
                user.checks.stream().filter(check -> check.enabled).forEach(check -> check.onEvent(e, user));
            }
        }
    }

}
