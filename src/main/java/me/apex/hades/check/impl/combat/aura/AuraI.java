package me.apex.hades.check.impl.combat.aura;

import cc.funkemunky.api.events.impl.PacketReceiveEvent;
import cc.funkemunky.api.tinyprotocol.api.Packet;
import me.apex.hades.check.api.Check;
import me.apex.hades.check.api.CheckInfo;
import me.apex.hades.objects.User;

@CheckInfo(name = "Aura", type = "I")
public class AuraI extends Check {

    @Override
    public void onPacket(PacketReceiveEvent e, User user) {
        if(e.getType().equalsIgnoreCase(Packet.Client.USE_ENTITY)) {

        }
    }

}
