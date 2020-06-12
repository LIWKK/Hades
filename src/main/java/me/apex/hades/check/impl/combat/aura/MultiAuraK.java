package me.apex.hades.check.impl.combat.aura;

import io.github.retrooper.packetevents.enums.EntityUseAction;
import io.github.retrooper.packetevents.event.impl.PacketReceiveEvent;
import io.github.retrooper.packetevents.packet.Packet;
import io.github.retrooper.packetevents.packetwrappers.in.useentity.WrappedPacketInUseEntity;
import me.apex.hades.check.api.Check;
import me.apex.hades.check.api.CheckInfo;
import me.apex.hades.objects.User;
import me.apex.hades.utils.PacketUtils;

@CheckInfo(name = "Aura", type = "K")
public class MultiAuraK extends Check {

    @Override
    public void onPacket(PacketReceiveEvent e, User user) {
        if (e.getPacketName().equalsIgnoreCase(Packet.Client.USE_ENTITY)){
            WrappedPacketInUseEntity packet = new WrappedPacketInUseEntity(e.getPacket());
            if (packet.getAction() == EntityUseAction.ATTACK){
                user.setEntitiesInteractedThisTick(user.getEntitiesInteractedThisTick() + 1);
                if (user.getEntitiesInteractedThisTick() > 1 && !user.isLagging()){
                    flag(user, "attacked " + user.getEntitiesInteractedThisTick() + " entities in a tick.");
                }
            }
        }else if (PacketUtils.isFlyingPacket(e.getPacketName())){
            user.setEntitiesInteractedThisTick(0);
        }
    }
}
