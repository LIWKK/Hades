package me.apex.hades.check.impl.packet.badpackets;

import cc.funkemunky.api.events.impl.PacketReceiveEvent;
import cc.funkemunky.api.tinyprotocol.api.Packet;
import cc.funkemunky.api.tinyprotocol.packet.in.WrappedInBlockPlacePacket;
import me.apex.hades.check.api.Check;
import me.apex.hades.check.api.CheckInfo;
import me.apex.hades.data.User;
import me.apex.hades.utils.PacketUtils;

@CheckInfo(Name = "BadPackets (H)", Type = Check.CheckType.PACKET, Experimental = false)
public class BadPacketsH extends Check {

    private int ticks;

    @Override
    public void onPacket(PacketReceiveEvent e, User user) {
        if(e.getType().equalsIgnoreCase(Packet.Client.BLOCK_PLACE))
        {
            WrappedInBlockPlacePacket packet = new WrappedInBlockPlacePacket(e.getPacket(), e.getPlayer());

            int ticks = this.ticks;
            this.ticks = 0;

            if(ticks < 2 && PacketUtils.isBlockPacket(packet.getItemStack().getType().toString()) && !user.isLagging())
            {
                if(vl++ > 4)
                    flag(user, "ticks = " + ticks);
            }else vl = 0;
        }else if(PacketUtils.isFlyingPacket(e.getType()))
        {
            ticks++;
        }
    }

}
