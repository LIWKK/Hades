package me.apex.hades.check.impl.packet.badpackets;

import cc.funkemunky.api.events.impl.PacketReceiveEvent;
import me.apex.hades.check.api.Check;
import me.apex.hades.check.api.CheckInfo;
import me.apex.hades.objects.User;

@CheckInfo(name = "BadPackets", type = "I")
public class BadPacketsI extends Check {

    @Override
    public void onPacket(PacketReceiveEvent e, User user) {

    }

}
