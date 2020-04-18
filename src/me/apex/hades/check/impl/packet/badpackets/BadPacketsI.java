package me.apex.hades.check.impl.packet.badpackets;

import me.apex.hades.check.Check;
import me.apex.hades.data.User;
import me.apex.hades.event.Event;
import me.apex.hades.event.impl.PacketEvent;
import me.apex.archive.network.packets.PacketPlayInBlockPlace;

public class BadPacketsI extends Check {

    public BadPacketsI(User user) {
        super(user);
        name = "BadPackets (I)";
        type = CheckType.PACKET;
    }

    @Override
    public void handle(Event event) {
        if(event instanceof PacketEvent)
        {
            PacketEvent e = (PacketEvent) event;
            if(e.getPacket() instanceof PacketPlayInBlockPlace)
            {
                PacketPlayInBlockPlace packet = (PacketPlayInBlockPlace) e.getPacket();

                debug("pos = " + packet.getBlockPos().toString());

                if(packet.getFace() == 255 && (packet.getBlockPos().getX() != -1 || packet.getBlockPos().getY() != -1 || packet.getBlockPos().getZ() != -1))
                {
                    flag("pos = " + packet.getBlockPos().toString());
                }
            }
        }
    }
}
