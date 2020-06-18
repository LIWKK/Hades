package me.apex.hades.event.impl;

import lombok.Getter;
import lombok.Setter;
import me.apex.hades.event.AnticheatEvent;
import me.apex.hades.event.Cancellable;
import me.apex.hades.tinyprotocol.api.Packet;
import me.apex.hades.user.User;
import me.apex.hades.utils.location.CustomLocation;
import org.bukkit.entity.Player;

@Getter
public class PacketEvent extends AnticheatEvent implements Cancellable {

    private Player player;

    @Setter
    private Object packet;

    @Setter
    private boolean cancelled;

    private String type;

    private Direction direction;

    private User user;

    private CustomLocation to, from;

    private long timeStamp;

    public PacketEvent(Player player, Object packet, String type, Direction direction, User user) {
        this.player = player;
        this.packet = packet;
        this.type = type;
        this.direction = direction;
        this.user = user;
        this.to = user.getTo();
        this.from = user.getFrom();

        timeStamp = System.currentTimeMillis();
    }

    public PacketEvent(Player player, Object packet, String type, Direction direction) {
        this.player = player;
        this.packet = packet;
        this.type = type;
        this.direction = direction;

        timeStamp = System.currentTimeMillis();
    }

    public boolean isPacketMovement() {
        return (type.equalsIgnoreCase(Packet.Client.POSITION) || type.equalsIgnoreCase(Packet.Client.FLYING) || type.equalsIgnoreCase(Packet.Client.POSITION_LOOK) || type.equalsIgnoreCase(Packet.Client.LOOK));
    }

    public enum Direction {
        CLIENT,
        SERVER
    }
}
