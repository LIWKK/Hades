package me.apex.hades.check.impl.movement.smallhop;

import io.github.retrooper.packetevents.event.impl.PacketReceiveEvent;
import me.apex.hades.check.api.Check;
import me.apex.hades.check.api.CheckInfo;
import me.apex.hades.objects.User;
import me.apex.hades.utils.PacketUtils;

//Credits to Moritz
@CheckInfo(name = "SmallHop", type = "A")
public class SmallHopA extends Check {
    int preVL = 0;
    
    @Override
    public void onPacket(PacketReceiveEvent e, User user) {
        if (PacketUtils.isFlyingPacket(e.getPacketName())){
            if(user.getDeltaY() == -user.getLastDeltaY() && user.getDeltaY() != 0 && user.getTeleportTicks() <= 0) {
                if(preVL++ > 1){
                    flag(user, "repetitive vertical motions, m: " + user.getDeltaY());
                    if(shouldMitigate()) lagBack(user);
                }
            }else preVL = 0;
        }
    }
}
