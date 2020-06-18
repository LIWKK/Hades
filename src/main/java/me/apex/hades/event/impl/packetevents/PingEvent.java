package me.apex.hades.event.impl.packetevents;

import me.apex.hades.event.AnticheatEvent;

public class PingEvent extends AnticheatEvent {

    private final long timeStamp;

    public PingEvent() {
        timeStamp = (System.nanoTime() / 1000000);
    }

    public long getTimeStamp() {
        return timeStamp;
    }

}
