package me.apex.hades.util;

import io.github.retrooper.packetevents.packet.Packet;

public class PacketUtil {

    public static boolean isFlyingPacket(String packetName) {
        return packetName.equalsIgnoreCase(Packet.Client.LOOK) || packetName.equalsIgnoreCase(Packet.Client.FLYING) || packetName.equalsIgnoreCase(Packet.Client.POSITION) || packetName.equalsIgnoreCase(Packet.Client.POSITION_LOOK);
    }

    public static boolean isPositionPacket(String packetName) {
        return packetName.equalsIgnoreCase(Packet.Client.POSITION) || packetName.equalsIgnoreCase(Packet.Client.POSITION_LOOK);
    }

    public static boolean isRotationPacket(String packetName) {
        return packetName.equalsIgnoreCase(Packet.Client.LOOK);
    }

    public static boolean isBlockPacket(String type) {
        return type.toLowerCase().contains("sword");
    }
}
