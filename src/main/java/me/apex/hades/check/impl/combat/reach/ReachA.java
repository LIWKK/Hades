package me.apex.hades.check.impl.combat.reach;

import me.apex.hades.check.api.Check;
import me.apex.hades.check.api.CheckInfo;
import me.apex.hades.objects.User;
import me.apex.hades.utils.AABB;
import me.apex.hades.utils.Ray;
import me.purplex.packetevents.enums.EntityUseAction;
import me.purplex.packetevents.event.impl.PacketReceiveEvent;
import me.purplex.packetevents.packet.Packet;
import me.purplex.packetevents.packetwrappers.in.useentity.WrappedPacketInUseEntity;
import org.bukkit.entity.Player;

@CheckInfo(name = "Reach", type = "A")
public class ReachA extends Check {
    int preVL = 0;

    @Override
    public void onPacket(PacketReceiveEvent e, User user) {
        if (e.getPacketName().equalsIgnoreCase(Packet.Client.USE_ENTITY)) {
        	WrappedPacketInUseEntity packet = new WrappedPacketInUseEntity(e.getPlayer(), e.getPacket());
            if (packet.getAction() == EntityUseAction.ATTACK && packet.getEntity() instanceof Player){
                try {
                    Player player = packet.getPlayer();
                    Player entity = (Player)packet.getEntity();
                    Ray ray = Ray.from(player);
                    double dis = AABB.from(entity).collidesD(ray,0, 10);
                    if (dis != -1) {
                        if (dis > 3.2){
                            if (preVL++ >= 2){
                                flag(user, "reach: " + dis);
                            }
                        }else preVL = 0;
                    }
                }catch (Exception ex){}
            }
        }
    }

}
