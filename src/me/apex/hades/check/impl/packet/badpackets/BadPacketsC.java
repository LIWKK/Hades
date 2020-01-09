package me.apex.hades.check.impl.packet.badpackets;

import cc.funkemunky.api.events.impl.PacketReceiveEvent;
import cc.funkemunky.api.tinyprotocol.api.Packet;
import cc.funkemunky.api.tinyprotocol.packet.in.WrappedInUseEntityPacket;
import me.apex.hades.check.api.Check;
import me.apex.hades.check.api.CheckInfo;
import me.apex.hades.data.User;
import me.apex.hades.utils.PacketUtils;

@CheckInfo(Name = "BadPackets (C)", Type = Check.CheckType.PACKET, Experimental = false)
public class BadPacketsC extends Check {

    private int ticks;

    @Override
    public void onPacket(PacketReceiveEvent e, User user) {
        if(e.getType().equalsIgnoreCase(Packet.Client.USE_ENTITY))
        {
            WrappedInUseEntityPacket packet = new WrappedInUseEntityPacket(e.getPacket(), e.getPlayer());
            if(packet.getAction() == WrappedInUseEntityPacket.EnumEntityUseAction.ATTACK)
            {
                int ticks = this.ticks;
                this.ticks = 0;

                if(ticks < 1 && !user.isLagging()) {
                    if(vl++ > 1)
                        flag(user, "ticks = " + ticks);
                }else vl = 0;
            }
        }else if(PacketUtils.isFlyingPacket(e.getType()))
        {
            ticks++;
        }
    }

}
