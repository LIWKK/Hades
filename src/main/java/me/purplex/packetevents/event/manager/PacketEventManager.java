package me.purplex.packetevents.event.manager;

import me.purplex.packetevents.event.PacketEvent;
import me.purplex.packetevents.event.listener.PacketListener;

import java.util.ArrayList;
import java.util.LinkedList;

public interface PacketEventManager {
    ArrayList<PacketListener> packetListeners = new ArrayList<>();
    void callEvent(PacketEvent e);

    void registerListener(PacketListener e);
    boolean unregisterListener(PacketListener e);

    boolean unregisterAllListeners();

    //public void sendPacket(Player player, WrappedPacket packet);

}
