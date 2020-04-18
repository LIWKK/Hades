package me.apex.hades.data.handlers;

import me.apex.hades.data.User;
import me.apex.hades.event.Event;
import me.apex.hades.event.EventHandler;
import me.apex.hades.event.impl.PacketEvent;
import me.apex.archive.network.packets.PacketPlayInFlying;
import me.apex.archive.network.packets.PacketPlayInKeepAlive;
import me.apex.archive.network.packets.PacketPlayInTransaction;

public class LagHandler extends EventHandler {

    public LagHandler(User user) {
        super(user);
    }

    private long lastFlying, lastKeepAlive, lastTransaction;
    private double avgFlyingDelay, avgKeepAlive, avgTransactionDelay;

    @Override
    public void handle(Event event) {
        if (event instanceof PacketEvent) {
            PacketEvent e = (PacketEvent) event;
            if (e.getPacket() instanceof PacketPlayInFlying) {
                user.data.sendTransaction();

                long cur = System.currentTimeMillis();
                long last = this.lastFlying;
                this.lastFlying = cur;

                if (!(last > 0)) return;

                long delay = Math.abs(cur - last);
                avgFlyingDelay = ((avgFlyingDelay * 19.0D) + delay) / 20.0D;

                if (avgFlyingDelay >= 60.0D && avgTransactionDelay >= 52.0D && avgKeepAlive > 4000)
                    user.data.lagging = true;
                else
                    user.data.lagging = false;
            } else if (e.getPacket() instanceof PacketPlayInKeepAlive) {
                long cur = System.currentTimeMillis();
                long last = this.lastKeepAlive;
                this.lastKeepAlive = cur;

                if (!(last > 0)) return;

                long delay = Math.abs(cur - last);
                avgKeepAlive = ((avgKeepAlive * 19.0D) + delay) / 20.0D;
            } else if (e.getPacket() instanceof PacketPlayInTransaction) {
                long cur = System.currentTimeMillis();
                long last = this.lastTransaction;
                this.lastTransaction = cur;

                if (!(last > 0)) return;

                long delay = Math.abs(cur - last);
                avgTransactionDelay = ((avgTransactionDelay * 19.0D) + delay) / 20.0D;
            }
        }
    }
}
