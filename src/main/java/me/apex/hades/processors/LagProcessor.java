package me.apex.hades.processors;

import lombok.Setter;
import me.apex.hades.tinyprotocol.api.Packet;
import me.apex.hades.user.User;

@Setter
public class LagProcessor {
    private User user;

    public void update(Object packet, String type) {
        if (user != null) {
            if (type.equalsIgnoreCase(Packet.Client.FLYING)) {
                long time = System.currentTimeMillis() - user.getLastMovePacket();
                if (time >= 100L) {
                    long diff = time - 50L;
                    user.setPacketsFromLag(user.getPacketsFromLag() + (int)Math.ceil(diff / 50.0)); //use this to check lag
                }
                else if (user.getPacketsFromLag() > 0) {
                    user.setPacketsFromLag(user.getPacketsFromLag() - 1);
                }
            }
            if (type.equalsIgnoreCase(Packet.Client.POSITION) || type.equalsIgnoreCase(Packet.Client.POSITION_LOOK) || type.equalsIgnoreCase(Packet.Client.LOOK)) {
                user.setLastMovePacket(System.currentTimeMillis());
            }
        }
    }
}
