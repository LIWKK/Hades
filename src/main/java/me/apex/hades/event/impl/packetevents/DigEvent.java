package me.apex.hades.event.impl.packetevents;

import me.apex.hades.event.AnticheatEvent;
import me.apex.hades.tinyprotocol.packet.in.WrappedInBlockDigPacket;
import me.apex.hades.tinyprotocol.packet.types.BaseBlockPosition;
import me.apex.hades.tinyprotocol.packet.types.EnumDirection;

public class DigEvent extends AnticheatEvent {

    private final BaseBlockPosition blockPos;
    private final EnumDirection direction;
    private final WrappedInBlockDigPacket.EnumPlayerDigType digType;

    public DigEvent(BaseBlockPosition blockPos, EnumDirection direction, WrappedInBlockDigPacket.EnumPlayerDigType digType) {
        this.blockPos = blockPos;
        this.direction = direction;
        this.digType = digType;
    }

    public BaseBlockPosition getBlockPos() {
        return blockPos;
    }

    public EnumDirection getDirection() {
        return direction;
    }

    public WrappedInBlockDigPacket.EnumPlayerDigType getDigType() {
        return digType;
    }

}
