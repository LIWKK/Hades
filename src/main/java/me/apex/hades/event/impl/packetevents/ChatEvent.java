package me.apex.hades.event.impl.packetevents;

import me.apex.hades.event.Event;

public class ChatEvent extends Event {

    private final String message;

    public ChatEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}
