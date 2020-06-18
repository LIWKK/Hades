package me.apex.hades.event.impl.packetevents;

import me.apex.hades.event.AnticheatEvent;

public class ChatEvent extends AnticheatEvent {

    private final String message;

    public ChatEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}
