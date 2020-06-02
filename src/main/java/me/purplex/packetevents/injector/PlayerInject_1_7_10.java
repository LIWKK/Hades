package me.purplex.packetevents.injector;

import me.purplex.packetevents.PacketEvents;
import me.purplex.packetevents.event.impl.PacketReceiveEvent;
import me.purplex.packetevents.event.impl.PacketSendEvent;
import me.purplex.packetevents.event.impl.PlayerInjectEvent;
import me.purplex.packetevents.event.impl.PlayerUninjectEvent;
import net.minecraft.util.io.netty.channel.Channel;
import net.minecraft.util.io.netty.channel.ChannelDuplexHandler;
import net.minecraft.util.io.netty.channel.ChannelHandlerContext;
import net.minecraft.util.io.netty.channel.ChannelPromise;
import org.bukkit.entity.Player;

class PlayerInject_1_7_10 {
    public static void injectPlayer(final Player player) {
        final ChannelDuplexHandler channelDuplexHandler = new ChannelDuplexHandler() {
            @Override
            public void channelRead(final ChannelHandlerContext ctx, final Object packet) throws Exception {
                String packetName = packet.getClass().getSimpleName();
                PacketReceiveEvent e = new PacketReceiveEvent(player, packetName, packet);
                PacketEvents.getEventManager().callEvent(e);
                if (e.isCancelled()) {
                    return;
                }
                super.channelRead(ctx, packet);
            }

            @Override
            public void write(final ChannelHandlerContext ctx, final Object packet, final ChannelPromise promise) throws Exception {
                String packetName = packet.getClass().getSimpleName();

                PacketSendEvent e = new PacketSendEvent(player, packetName, packet);
                PacketEvents.getEventManager().callEvent(e);
                if (e.isCancelled()) {
                    return;
                }

                super.write(ctx, packet, promise);
            }
        };
        final PlayerInjectEvent injectEvent = new PlayerInjectEvent(player);
        PacketEvents.getEventManager().callEvent(injectEvent);
        getChannel(player).pipeline().addBefore("packet_handler", player.getName(), channelDuplexHandler);
    }

    public static void uninjectPlayer(final Player player) {
        final Channel channel = getChannel(player);
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                channel.pipeline().remove(player.getName());
            }
        };
        final PlayerUninjectEvent uninjectEvent = new PlayerUninjectEvent(player);
        PacketEvents.getEventManager().callEvent(uninjectEvent);
        channel.eventLoop().submit(runnable);
    }

    public static Channel getChannel(final Player player) {
        return (Channel) ChannelManager.getChannel(player);
    }
}
