package me.apex.hades.event.impl.packetevents;

import me.apex.hades.event.Event;

public class SwingEvent extends Event {

    private final long timeStamp;

    public SwingEvent() {
        timeStamp = (System.nanoTime() / 1000000);
    }

    public long getTimeStamp() {
        return timeStamp;
    }

}
