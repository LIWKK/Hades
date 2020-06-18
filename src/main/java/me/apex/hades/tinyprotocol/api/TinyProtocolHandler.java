package me.apex.hades.tinyprotocol.api;

import lombok.Getter;
import me.apex.hades.HadesPlugin;
import me.apex.hades.event.impl.PacketEvent;
import me.apex.hades.tinyprotocol.api.packets.ChannelInjector;
import me.apex.hades.user.User;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class TinyProtocolHandler {
    @Getter
    private static ChannelInjector instance;

    public boolean paused = false;

    public TinyProtocolHandler() {
        // 1.8+ and 1.7 NMS have different class paths for their libraries used. This is why we have to separate the two.
        // These feed the packets asynchronously, before Minecraft processes it, into our own methods to process and be used as an API.

        instance = new ChannelInjector();

        Bukkit.getPluginManager().registerEvents(instance, HadesPlugin.getInstance());
    }

    // Purely for making the code cleaner
    public static void sendPacket(Player player, Object packet) {
        instance.getChannel().sendPacket(player, packet);
    }

    public static ProtocolVersion getProtocolVersion(Player player) {
        return instance.getChannel().getProtocolVersion(player);
    }



    public Object onPacketOutAsync(Player sender, Object packet) {

        if(!paused) {

            String name = packet.getClass().getName();
            int index = name.lastIndexOf(".");
            String packetName = name.substring(index + 1);
            User user = HadesPlugin.userManager.getUser(sender.getUniqueId());
            PacketEvent event = new PacketEvent(sender, packet, packetName, PacketEvent.Direction.SERVER);

            if (user != null && (System.currentTimeMillis() - user.getTimestamp()) > 500L) {
                event = new PacketEvent(sender, packet, packetName, PacketEvent.Direction.SERVER, user);
            }
            HadesPlugin.getInstance().getEventManager().callEvent(event);


            return !event.isCancelled() ? event.getPacket() : null;
        } else return packet;
    }

    public Object onPacketInAsync(Player sender, Object packet) {
        if (!paused) {

            String name = packet.getClass().getName();
            int index = name.lastIndexOf(".");
            String packetName = name.substring(index + 1).replace("PacketPlayInUseItem", "PacketPlayInBlockPlace").replace(Packet.Client.LEGACY_LOOK, Packet.Client.LOOK).replace(Packet.Client.LEGACY_POSITION, Packet.Client.POSITION).replace(Packet.Client.LEGACY_POSITION_LOOK, Packet.Client.POSITION_LOOK);

            User user = HadesPlugin.userManager.getUser(sender.getUniqueId());
            PacketEvent event = new PacketEvent(sender, packet, packetName, PacketEvent.Direction.CLIENT);

            if (user != null && (System.currentTimeMillis() - user.getTimestamp()) > 500L) {
                event = new PacketEvent(sender, packet, packetName, PacketEvent.Direction.CLIENT, user);
            }

            if (user != null) {
                PacketEvent finalEvent = event;
                user.getExecutorService().execute(() -> HadesPlugin.getInstance().getEventManager().callEvent(finalEvent));
            }

            return !event.isCancelled() ? event.getPacket() : null;
        }
        return packet;
    }
}

