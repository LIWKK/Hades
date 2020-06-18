package me.apex.hades.event.impl.packetevents;

import lombok.Getter;
import lombok.Setter;
import me.apex.hades.event.AnticheatEvent;

@Getter
@Setter
public class FlyingPacketEvent extends AnticheatEvent {

    private double x;
    private double y;
    private double z;
    private float yaw;
    private float pitch;
    private boolean isOnGround;
    private boolean clientMoved;
    private boolean clientLooked;

    public FlyingPacketEvent(double x, double y, double z, float yaw, float pitch, boolean isOnGround, boolean clientMoved, boolean clientLooked) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
        this.isOnGround = isOnGround;
        this.clientMoved = clientMoved;
        this.clientLooked = clientLooked;
    }
}
