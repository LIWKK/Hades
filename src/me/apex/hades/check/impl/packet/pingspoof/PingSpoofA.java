package me.apex.hades.check.impl.packet.pingspoof;

import me.apex.hades.check.Check;
import me.apex.hades.data.User;
import me.apex.hades.event.Event;
import me.apex.hades.event.impl.PacketEvent;
import me.apex.archive.network.packets.PacketPlayInFlying;
import me.apex.archive.network.packets.PacketPlayInTransaction;

public class PingSpoofA extends Check {

    public PingSpoofA(User user) {
        super(user);
        name = "PingSpoof (A)";
        type = CheckType.PACKET;
    }

    private long lastSent;

    @Override
    public void handle(Event event) {
        if (event instanceof PacketEvent) {
            PacketEvent e = (PacketEvent) event;
            if (e.getPacket() instanceof PacketPlayInFlying) {
                user.data.sendTransaction();
                lastSent = System.currentTimeMillis();
            } else if (e.getPacket() instanceof PacketPlayInTransaction) {
                long transactionDiff = Math.abs(System.currentTimeMillis() - lastSent);
                long keepAliveDiff = Math.abs(System.currentTimeMillis() - user.data.lastKeepAlive);
                if (keepAliveDiff > 5200 && transactionDiff < 60 && !user.data.lagging)
                    flag("ping-delay = " + keepAliveDiff + ", transaction-delay = " + transactionDiff);
            }
        }
    }
}
