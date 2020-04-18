package me.apex.hades.event.impl;

import cc.funkemunky.api.events.impl.PacketReceiveEvent;
import me.apex.hades.event.Event;
import me.apex.archive.network.Packet;

public class PacketEvent extends Event {

    private PacketReceiveEvent packetHandler;

    public PacketEvent(PacketReceiveEvent packetHandler)
    {
        this.packetHandler = packetHandler;
    }

    public PacketReceiveEvent getPacketHandler() { return packetHandler; }

}
