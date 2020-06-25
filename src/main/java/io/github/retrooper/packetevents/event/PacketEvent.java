package io.github.retrooper.packetevents.event;


import io.github.retrooper.packetevents.PacketEvents;
import io.github.retrooper.packetevents.enums.ServerVersion;

/**
 * Incoming and sent packets are called asynchronously,
 * recommended to avoid non thread safe bukkit operations
 */
public class PacketEvent {
    protected static ServerVersion version = PacketEvents.getServerVersion();
    protected long timestamp = PacketEvents.highlyPreciseMillis();

    /**
     * Timestamp of the packet
     *
     * @return
     */
    public long getTimestamp() {
        return timestamp;
    }
}
