package me.apex.hades.data;

import me.apex.hades.utils.TextFile;
import me.apex.archive.network.OldPacketProcessor;
import me.apex.hades.utils.Location;

import java.lang.reflect.Constructor;

public class UserData {

    public int ping;
    public Location location, lastLocation;
    public org.bukkit.Location bukkitLocation, lastBukkitLocation;
    public boolean alerts, inventoryOpen, lagging, digging, bypass;
    public double deltaXZ, lastDeltaXZ, deltaY, lastDeltaY;
    public float deltaYaw, lastDeltaYaw, deltaPitch, lastDeltaPitch;
    public long lastOnIce, lastOnSlime, lastHit, lastJoin, lastTeleport, lastVelocity, lastKeepAlive, lastServerKeepAlive;
    public TextFile logFile;
    public String ip;

    public OldPacketProcessor packetHandler;

    public void sendTransaction()
    {
        try{
            Constructor<?> transactionConstructor = packetHandler.getNMSClass("PacketPlayOutTransaction").getConstructor(int.class, short.class, boolean.class);
            Object packet = transactionConstructor.newInstance((int)0, (short)0, false);
            packetHandler.sendPacket(packet);
        }catch(Exception x) { x.printStackTrace(); }
    }

    public void sendCustomPayload(String channel)
    {
        try{
            Constructor<?> transactionConstructor = packetHandler.getNMSClass("PacketPlayOutCustomPayload").getConstructor(String.class, packetHandler.getNMSClass("PacketDataSerializer"));
            Object packet = transactionConstructor.newInstance(channel, null);
            packetHandler.sendPacket(packet);
        }catch(Exception x) { x.printStackTrace(); }
    }

}
