package me.apex.hades.managers;

import me.apex.hades.data.User;
import me.apex.hades.event.Event;
import me.apex.hades.event.EventHandler;
import me.apex.hades.event.impl.PacketEvent;
import me.apex.archive.network.packets.PacketPlayInCustomPayload;
import me.apex.hades.utils.PlayerUtils;

public enum EventManager {
    INSTANCE;

    public void call(Event event, User user) {
        for (EventHandler handler : user.handlers) {
            //Exceptions
            if (event instanceof PacketEvent) {
                PacketEvent e = (PacketEvent) event;
                if (!(e.getPacket() instanceof PacketPlayInCustomPayload) && PlayerUtils.timeSinceJoin(user) < 5000 || user.data.bypass)
                    return;
            }

            //Handle Event
            event.timeStamp = System.currentTimeMillis();
            handler.handle(event);
        }
    }
}

