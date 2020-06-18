package me.apex.hades.event.impl.packetevents;

import me.apex.hades.event.AnticheatEvent;

public class SwingEvent extends AnticheatEvent {

    private final long timeStamp;

    public SwingEvent() {
        timeStamp = (System.nanoTime() / 1000000);
    }

    public long getTimeStamp() {
        return timeStamp;
    }

}
