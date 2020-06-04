package me.purplex.packetevents.event.impl;

import me.purplex.packetevents.PacketEvents;
import me.purplex.packetevents.event.PacketEvent;

//unfinished
public class ServerReloadEvent extends PacketEvent {
    private final long currentTime;

    public ServerReloadEvent(final long currentTime){
        this.currentTime = currentTime;
    }

    public ServerReloadEvent() {
        this.currentTime = PacketEvents.currentTimeMS();
    }

    public final long getCurrentTime() {
        return currentTime;
    }
}
