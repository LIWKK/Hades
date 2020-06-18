package me.apex.hades.event.impl;

import lombok.Getter;
import lombok.Setter;
import me.apex.hades.event.AnticheatEvent;
import me.apex.hades.event.Cancellable;

@Getter
public class ServerShutdownEvent extends AnticheatEvent implements Cancellable {

    @Setter
    private Object packet;

    @Setter
    private boolean cancelled;



    public ServerShutdownEvent() {
    }
}
