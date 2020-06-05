package me.apex.hades.check.impl.combat.aim;

import io.github.retrooper.packetevents.event.impl.PacketReceiveEvent;
import io.github.retrooper.packetevents.packet.Packet;
import me.apex.hades.check.api.Check;
import me.apex.hades.check.api.CheckInfo;
import me.apex.hades.objects.User;

@CheckInfo(name = "Aim", type = "D")
public class AimD extends Check {

    @Override
    public void onPacket(PacketReceiveEvent e, User user) {
        if (e.getPacketName().equalsIgnoreCase(Packet.Client.POSITION_LOOK) || e.getPacketName().equalsIgnoreCase(Packet.Client.LOOK)) {
            float pitch = user.getDeltaPitch();
            if ((Math.round(pitch) == pitch || pitch % 1 == 0) && pitch != 0) {
                reset(1000);
                if (vl++ > 3) {
                    flag(user, "pitch = " + Math.round(pitch));
                }
            }
        }
    }

}
