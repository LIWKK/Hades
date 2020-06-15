package me.apex.hades.processor;

import io.github.retrooper.packetevents.event.impl.PacketReceiveEvent;
import me.apex.hades.user.User;

public abstract class Processor {

    public final User user;

    public Processor(User user) {
        this.user = user;
    }

    public abstract void process(PacketReceiveEvent e);

}
