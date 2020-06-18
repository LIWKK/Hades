package me.apex.hades.event.impl.packetevents;

import me.apex.hades.event.AnticheatEvent;
import me.apex.hades.tinyprotocol.packet.in.WrappedInEntityActionPacket;

public class EntityActionEvent extends AnticheatEvent {

    private final WrappedInEntityActionPacket.EnumPlayerAction action;

    public EntityActionEvent(WrappedInEntityActionPacket.EnumPlayerAction action) {
        this.action = action;
    }

    public WrappedInEntityActionPacket.EnumPlayerAction getAction() {
        return action;
    }

}
