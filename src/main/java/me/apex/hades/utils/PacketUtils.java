package me.apex.hades.utils;

import java.lang.reflect.Constructor;

import org.bukkit.Bukkit;

import me.apex.hades.objects.User;
import me.purplex.packetevents.packet.Packet;

public class PacketUtils {

    /*
    Packet Utils
     */

    public static boolean isFlyingPacket(String type) {
        return type.equalsIgnoreCase(Packet.Client.LOOK) || type.equalsIgnoreCase(Packet.Client.FLYING) || type.equalsIgnoreCase(Packet.Client.POSITION) || type.equalsIgnoreCase(Packet.Client.POSITION_LOOK);
    }

    public static boolean isBlockPacket(String type) {
        return type.toLowerCase().contains("sword");
    }

    /*
    Packet Types
     */

    public static void sendKeepAlive(User user) {
        try {
            Constructor<?> keepAliveConstructor = getNMSClass("PacketPlayOutKeepAlive").getConstructor(int.class);
            Object packet = keepAliveConstructor.newInstance(-1);
            sendPacket(packet, user);
        } catch (Exception x) {
            x.printStackTrace();
        }
    }

    public static void sendTransaction(User user) {
        try {
            Constructor<?> transactionConstructor = getNMSClass("PacketPlayOutTransaction").getConstructor(int.class, short.class, boolean.class);
            Object packet = transactionConstructor.newInstance((int) 0, (short) 0, false);
            sendPacket(packet, user);
        } catch (Exception x) {
            x.printStackTrace();
        }
    }

    /*
    Packet Methods
     */

    public static void sendPacket(Object packet, User user) {
        try {
            Object handle = user.getPlayer().getClass().getMethod("getHandle").invoke(user.getPlayer());
            Object playerConnection = handle.getClass().getField("playerConnection").get(handle);
            playerConnection.getClass().getMethod("sendPacket", getNMSClass("Packet")).invoke(playerConnection, packet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Class<?> getNMSClass(String name) {
        String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
        try {
            return Class.forName("net.minecraft.server." + version + "." + name);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
