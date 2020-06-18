package me.apex.hades.event.impl.packetevents;

import me.apex.hades.event.Event;

public class PingEvent extends Event {

    private final long timeStamp;

    public PingEvent() {
        timeStamp = (System.nanoTime() / 1000000);
    }

    public long getTimeStamp() {
        return timeStamp;
    }

}
