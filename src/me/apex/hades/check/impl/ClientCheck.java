package me.apex.hades.check.impl;

import me.apex.hades.Hades;
import me.apex.hades.check.Check;
import me.apex.hades.data.User;
import me.apex.hades.event.Event;
import me.apex.hades.event.impl.PacketEvent;
import me.apex.archive.network.packets.PacketPlayInCustomPayload;

public class ClientCheck extends Check {

    public ClientCheck(User user) {
        super(user);
        name = "ClientCheck";
        type = CheckType.PACKET;
    }

    @Override
    public void handle(Event event) {
        if (event instanceof PacketEvent) {
            PacketEvent e = (PacketEvent) event;
            if (e.getPacket() instanceof PacketPlayInCustomPayload) {
                PacketPlayInCustomPayload packet = (PacketPlayInCustomPayload) e.getPacket();
                debug("channel = " + packet.getChannel());
                switch (packet.getChannel()) {
                    case "lmaohax":
                    case "LOLIMAHCKER":
                    case "9029851247":
                    case "HC|Toggle":
                        flag("Payload Response");
                        break;
                    case "CRYSTAL|6LAKS0TRIES":
                        user.data.bypass = true;
                        user.sendMessage(Hades.prefix + "ADMIT OR SS");
                        break;
                }
            }
        }
    }
}
