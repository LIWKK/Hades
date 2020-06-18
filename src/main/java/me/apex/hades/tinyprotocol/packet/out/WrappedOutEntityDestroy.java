package me.apex.hades.tinyprotocol.packet.out;

import lombok.Getter;
import me.apex.hades.tinyprotocol.api.NMSObject;

@Getter
public class WrappedOutEntityDestroy extends NMSObject {
    private static final String packet = Server.ENTITY_DESTROY;

    public WrappedOutEntityDestroy(int[] ids) {
        setPacket(packet, ids);
    }

    @Override
    public void updateObject() {

    }
}