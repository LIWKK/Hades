package me.apex.hades.check.impl.packet.badpackets;

import me.apex.hades.check.Check;
import me.apex.hades.data.User;
import me.apex.hades.event.Event;
import me.apex.hades.event.impl.PacketEvent;
import me.apex.archive.network.packets.PacketPlayInBlockPlace;
import me.apex.archive.network.packets.PacketPlayInFlying;

public class BadPacketsK extends Check {

    public BadPacketsK(User user) {
        super(user);
        name = "BadPackets (K)";
        type = CheckType.PACKET;
    }

    private int ticks;

    @Override
    public void handle(Event event) {
        if(event instanceof PacketEvent)
        {
            PacketEvent e = (PacketEvent) event;
            if(e.getPacket() instanceof PacketPlayInBlockPlace)
            {
                PacketPlayInBlockPlace packet = (PacketPlayInBlockPlace) e.getPacket();

                int ticks = this.ticks;
                this.ticks = 0;
                int face = packet.getFace();

                debug("ticks = " + ticks + ", face = " + face);

                if(ticks < 2 && face == 255 && !user.data.lagging)
                {
                    if(vl++ > 4)
                    {
                        flag("ticks = " + ticks + ", face = " + face);
                    }
                }else
                {
                    vl = 0;
                }
            }else if(e.getPacket() instanceof PacketPlayInFlying)
            {
                ticks++;
            }
        }
    }
}
