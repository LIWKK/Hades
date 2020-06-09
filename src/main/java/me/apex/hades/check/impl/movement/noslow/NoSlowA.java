package me.apex.hades.check.impl.movement.noslow;

import io.github.retrooper.packetevents.enums.ClientVersion;
import io.github.retrooper.packetevents.event.impl.PacketReceiveEvent;
import me.apex.hades.check.api.Check;
import me.apex.hades.check.api.CheckInfo;
import me.apex.hades.objects.User;
import me.apex.hades.utils.PacketUtils;

@CheckInfo(name = "NoSlow", type = "A")
public class NoSlowA extends Check {
    int preVL;

    @Override
    public void onPacket(PacketReceiveEvent e, User user) {
        if (PacketUtils.isFlyingPacket(e.getPacketName())){
            //Patches vanilla noslow and badpackets detect NCP bypass so yea on blocking noslow were very fine!
            if (user.clientVersion == ClientVersion.v_1_8){
                if (user.isSprinting() && user.getPlayer().isBlocking()){
                    if (++preVL >= 2) flag(user, "not slowing down while blocking sword.");
                }else preVL = 0;
            }
        }
    }
}
