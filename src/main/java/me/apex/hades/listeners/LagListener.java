package me.apex.hades.listeners;

import cc.funkemunky.api.events.AtlasListener;
import cc.funkemunky.api.events.Listen;
import cc.funkemunky.api.events.impl.PacketReceiveEvent;
import cc.funkemunky.api.events.impl.PacketSendEvent;
import cc.funkemunky.api.tinyprotocol.api.Packet;
import cc.funkemunky.api.utils.Init;
import me.apex.hades.objects.User;
import me.apex.hades.objects.UserManager;
import me.apex.hades.utils.MathUtils;
import me.apex.hades.utils.PacketUtils;

@Init
public class LagListener implements AtlasListener {

    @Listen
    public void onPacketReceived(PacketReceiveEvent e) {
        if (UserManager.INSTANCE.getUser(e.getPlayer().getUniqueId()) != null) {
            User user = UserManager.INSTANCE.getUser(e.getPlayer().getUniqueId());
            if (PacketUtils.isFlyingPacket(e.getType())) {
                double diff = e.getTimeStamp() - user.getLastPacket();
                double value = 50;
                value -= diff;

                if (value < -80) {
                    double lastLag = e.getTimeStamp() - user.getLastLagPacket();

                    if (lastLag < 900) {
                        user.setLagging(true);
                        user.setLastLagSet(e.getTimeStamp());
                    } else
                        user.setLagging(false);

                    user.setLastLagPacket(e.getTimeStamp());
                }

                if (e.getTimeStamp() - user.getLastLagSet() > 1000)
                    user.setLagging(false);

                user.setLastPacket(e.getTimeStamp());
                PacketUtils.sendTransaction(user);
            } else if (e.getType().equalsIgnoreCase(Packet.Client.KEEP_ALIVE)) {
                user.setLastKeepAlive(e.getTimeStamp());
                user.setPing((int) Math.abs(e.getTimeStamp() - user.getLastServerKeepAlive()));
            } else if (e.getType().equalsIgnoreCase(Packet.Client.TRANSACTION)) {
                user.getTransactionQueue().add(e.getTimeStamp());
                if (user.getTransactionQueue().size() == 50) {
                    double deviation = MathUtils.getStandardDeviation(user.getTransactionQueue().stream().mapToLong(l -> l).toArray());

                    if (deviation <= 720 && user.isLagging())
                        user.setLagging(false);

                    user.getTransactionQueue().clear();
                }
            }
        }
    }

    @Listen
    public void onPacketSend(PacketSendEvent e) {
        if (UserManager.INSTANCE.getUser(e.getPlayer().getUniqueId()) != null) {
            User user = UserManager.INSTANCE.getUser(e.getPlayer().getUniqueId());
            if (e.getType().equalsIgnoreCase(Packet.Server.KEEP_ALIVE))
                user.setLastServerKeepAlive(e.getTimeStamp());
        }
    }

}
